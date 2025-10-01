pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "tanish688/scientific-calculator:1.0"
        DOCKERHUB_CREDENTIALS = "dockerhub-creds"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/Tanish-pat/scientific-calculator.git'
            }
        }

        stage('Build & Test') {
            agent {
                docker {
                    image 'maven:3.9.2-eclipse-temurin-17'
                    args '-v /var/run/docker.sock:/var/run/docker.sock'
                }
            }
            steps {
                sh 'mvn clean test'
                sh 'mvn package'
            }
        }

        stage('Prepare Docker Images') {
            steps {
                sh 'docker pull openjdk:17-jdk-alpine || true'
            }
        }

        stage('Docker Build') {
            steps {
                sh "docker build -t ${DOCKER_IMAGE} ."
            }
        }

        stage('Docker Push') {
            steps {
                withCredentials([usernamePassword(credentialsId: "${DOCKERHUB_CREDENTIALS}",
                                                  passwordVariable: 'DOCKER_PASS',
                                                  usernameVariable: 'DOCKER_USER')]) {
                    sh 'echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin'
                    sh "docker push ${DOCKER_IMAGE}"
                }
            }
        }

        stage('Deploy with Ansible') {
            steps {
                sh 'ansible-playbook -i ansible/hosts.ini ansible/deploy.yml'
            }
        }
    }

    post {
        always {
            cleanWs()
        }
        success {
            emailext(
                subject: "SUCCESS: Scientific Calculator Pipeline",
                body: "The Jenkins pipeline for scientific calculator has completed successfully.\n\nCheck console output at: ${env.BUILD_URL}",
                to: "Tanish.Pathania@iiitb.ac.in"
            )
        }

        failure {
            emailext(
                subject: "FAILURE: Scientific Calculator Pipeline",
                body: "The Jenkins pipeline for scientific calculator has failed.\n\nCheck console output at: ${env.BUILD_URL}",
                to: "Tanish.Pathania@iiitb.ac.in"
            )
        }
    }
}