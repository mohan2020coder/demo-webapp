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
                   sh 'docker build -t monihub/demo-webapp .'
               }
           }
       }

        stage('Push Docker Image') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'dockerhub-credentials-id', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                        sh '''
                            echo "${DOCKER_PASSWORD}" | docker login --username "${DOCKER_USERNAME}" --password-stdin
                            docker push monihub/demo-webapp
                        '''
                    }
                }
            }
        }


        stage('Deploy') {
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
