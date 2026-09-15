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