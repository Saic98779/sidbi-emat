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
                git branch: 'main',
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
                -v /home/ubuntu/uploads:/home/ubuntu/uploads \
                ${IMAGE_NAME}
        '''
            }
        }

        stage('Health Check') {
            steps {
                sh '''
                echo "Waiting for application to start..."
                sleep 30

                curl -f http://localhost:${HOST_PORT}/emat/v1/health
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