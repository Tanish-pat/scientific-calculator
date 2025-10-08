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
                withCredentials([usernamePassword(credentialsId: "${DOCKERHUB_CREDENTIALS}",passwordVariable: 'DOCKER_PASS',usernameVariable: 'DOCKER_USER')]) {
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

        stage('Verify Deployment') {
            steps {
                sh 'docker ps | grep scientific-calculator || echo "Container not running"'
            }
        }
    }

    post {
        success {
            emailext(
                to: 'Tanish.Pathania@iiitb.ac.in',
                subject: "✅ Build SUCCESS: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: """\
                    The Jenkins pipeline completed successfully.

                    Job: ${env.JOB_NAME}
                    Build: #${env.BUILD_NUMBER}
                    Status: SUCCESS
                    Time: ${new Date()}
                    """
            )
        }
        failure {
            emailext(
                to: 'Tanish.Pathania@iiitb.ac.in',
                subject: "❌ Build FAILURE: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: """\
                    The Jenkins pipeline failed.

                    Job: ${env.JOB_NAME}
                    Build: #${env.BUILD_NUMBER}
                    Status: FAILURE
                    Time: ${new Date()}
                    """
            )
        }
        always {
            cleanWs()
        }
    }
}