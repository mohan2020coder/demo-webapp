pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'your-dockerhub-username/demo-webapp:latest'
        SSH_CREDENTIALS_ID = 'your-ssh-credentials-id'
        REMOTE_SERVER = 'user@remote-server-ip'
        REMOTE_DEPLOY_PATH = '/path/to/deploy'
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/mohan2020coder/demo-webapp.git'
            }
        }

        stage('Build') {
            steps {
                script {
                    docker.build(DOCKER_IMAGE)
                }
            }
        }

        stage('Push') {
            steps {
                script {
                    docker.withRegistry('https://index.docker.io/v1/', 'dockerhub-credentials-id') {
                        docker.image(DOCKER_IMAGE).push()
                    }
                }
            }
        }

        stage('Deploy') {
            steps {
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

    post {
        always {
            cleanWs()
        }
    }
}
