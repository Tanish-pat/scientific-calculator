pipeline {
    agent {
        docker {
            image 'docker:28.4-dind'       // Docker-in-Docker image
            args '-v /var/run/docker.sock:/var/run/docker.sock'
        }
    }

    environment {
        DOCKER_IMAGE = "tanish688/scientific-calculator:1.0"
        DOCKERHUB_CREDENTIALS = "dockerhub-creds" // Jenkins credentials ID
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/Tanish-pat/scientific-calculator.git'
            }
        }

        stage('Build & Test') {
            steps {
                sh 'mvn clean test'
                sh 'mvn package'
            }
        }

        stage('Docker Build') {
            steps {
                sh "docker build -t ${DOCKER_IMAGE} ."
            }
        }

        stage('Docker Push') {
            steps {
                withCredentials([usernamePassword(credentialsId: "${DOCKERHUB_CREDENTIALS}", passwordVariable: 'DOCKER_PASS', usernameVariable: 'DOCKER_USER')]) {
                    sh 'echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin'
                    sh "docker push ${DOCKER_IMAGE}"
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