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
                withCredentials([
                        string(
                                credentialsId: 'VAULT_ROLE_ID',
                                variable: 'VAULT_ROLE_ID'
                        ),
                        string(
                                credentialsId: 'VAULT_SECRET_ID',
                                variable: 'VAULT_SECRET_ID'
                        )
                ]) {
                    sh '''
                        docker stop ${APP_NAME} || true
                        docker rm ${APP_NAME} || true

                        docker run -d \
                            --network host \
                            --name ${APP_NAME} \
                            --restart unless-stopped \
                            -e SPRING_PROFILES_ACTIVE=prod \
                            -e VAULT_SCHEME=https \
                            -e VAULT_HOST=vault-emat.metaversedu.in \
                            -e VAULT_PORT=443 \
                            -e VAULT_ROLE_ID="$VAULT_ROLE_ID" \
                            -e VAULT_SECRET_ID="$VAULT_SECRET_ID" \
                            -v /home/ubuntu/uploads:/home/ubuntu/uploads \
                            ${IMAGE_NAME}
                    '''
                }
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