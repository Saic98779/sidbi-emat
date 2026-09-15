pipeline {
    agent any

    environment {
        APP_NAME = "emat-app"
        IMAGE_NAME = "emat:latest"
        CONTAINER_PORT = "8086"
        HOST_PORT = "8086"
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'prod',
                        credentialsId: 'github-token',
                        url: 'https://github.com/Saic98779/sidbi-emat.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh '''
                    docker build -t ${IMAGE_NAME} .
                '''
            }
        }

        stage('Check Deployment Config') {
            steps {
                sh '''
            echo "========================================"
            echo "Jenkins deployment configuration"
            echo "========================================"

            echo "Current user:"
            whoami

            echo "Workspace:"
            pwd

            echo "Git commit:"
            git rev-parse HEAD

            echo "Git branch:"
            git branch --show-current

            echo "Checking env file:"
            if [ -r /opt/emat-config/.env ]; then
                echo "ENV_FILE_READABLE=YES"
            else
                echo "ENV_FILE_READABLE=NO"
            fi

            echo "Checking Vault variables WITHOUT showing values:"
            grep -E '^(VAULT_ROLE_ID|VAULT_SECRET_ID)=' /opt/emat-config/.env \
                | sed 's/=.*$/=<SET>/'

            echo "========================================"
        '''
            }
        }

        stage('Deploy') {
            steps {
                sh '''
                    docker stop ${APP_NAME} || true
                    docker rm ${APP_NAME} || true

                    docker run -d \
                        --network host \
                        --name ${APP_NAME} \
                        --restart unless-stopped \
                        --env-file /opt/emat-config/.env \
                        -e SPRING_PROFILES_ACTIVE=prod \
                        -e VAULT_SCHEME=https \
                        -e VAULT_HOST=vault-emat.metaversedu.in \
                        -e VAULT_PORT=443 \
                        -v /home/ubuntu/uploads:/home/ubuntu/uploads \
                        ${IMAGE_NAME}
                '''
            }
        }

        stage('Verify Deployment') {
            steps {
                sh '''
                    echo "Waiting for application to start..."
                    sleep 10

                    if ! docker ps --filter "name=${APP_NAME}" --filter "status=running" | grep -q "${APP_NAME}"; then
                        echo "Application container is not running"
                        docker logs --tail=100 ${APP_NAME} || true
                        exit 1
                    fi

                    echo "Application container is running"
                    docker logs --tail=50 ${APP_NAME}
                '''
            }
        }
    }

    post {
        success {
            echo 'Deployment Successful'
        }

        failure {
            sh 'docker logs --tail=100 ${APP_NAME} || true'
            echo 'Deployment Failed'
        }
    }
}