pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'monihub/demo-webapp:latest'
        SSH_CREDENTIALS_ID = 'SSH_CREDENTIALS_ID'
        REMOTE_SERVER = 'adminuser@192.168.0.104'
        REMOTE_DEPLOY_PATH = '/usr/local/tomcat/webapps'
    }

    stages {
        stage('Debug') {
            steps {
                sh 'echo "Current directory: ${PWD}"'
                sh 'ls -la'
            }
        }

        stage('Checkout') {
            steps {
                script {
                    // Checking out the Git repository
                    git branch: 'main', url: 'https://github.com/mohan2020coder/demo-webapp.git'
                    //git 'https://github.com/mohan2020coder/demo-webapp.git'
                }
            }
        }

   
            stage('Build Docker Image') {
                steps {
                    script {
                        docker.withRegistry('https://index.docker.io/v1/', 'dockerhub-credentials-id') {
                            def customImage = docker.build('monihub/demo-webapp:latest', '.')
                            customImage.push()
                        }
                    }
                }
            }
        

        stage('Deploy to Server') {
            steps {
                script {
                    // Deploying to remote server via SSH
                    sshagent([SSH_CREDENTIALS_ID]) {
                        sh """
                        ssh -o StrictHostKeyChecking=no ${REMOTE_SERVER} << EOF
                        docker pull ${DOCKER_IMAGE}
                        docker stop demo-webapp || true
                        docker rm demo-webapp || true
                        docker run -d -p 8080:8080 --name demo-webapp ${DOCKER_IMAGE}
                        EOF
                        """
                    }
                }
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}
