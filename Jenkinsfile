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
                script {
                    env.TEST_OUTPUT = sh(script: 'mvn clean test', returnStdout: true).trim()
                    env.PACKAGE_OUTPUT = sh(script: 'mvn package', returnStdout: true).trim()
                }
            }
        }

        stage('Prepare Docker Images') {
            steps {
                script {
                    env.DOCKER_PULL_OUTPUT = sh(script: 'docker pull openjdk:17-jdk-alpine || true', returnStdout: true).trim()
                }
            }
        }

        stage('Docker Build') {
            steps {
                script {
                    env.DOCKER_BUILD_OUTPUT = sh(script: "docker build -t ${DOCKER_IMAGE} .", returnStdout: true).trim()
                }
            }
        }

        stage('Docker Push') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: "${DOCKERHUB_CREDENTIALS}",
                                                    passwordVariable: 'DOCKER_PASS',
                                                    usernameVariable: 'DOCKER_USER')]) {
                        env.DOCKER_PUSH_OUTPUT = sh(script: """
                            echo \$DOCKER_PASS | docker login -u \$DOCKER_USER --password-stdin
                            docker push ${DOCKER_IMAGE}
                        """, returnStdout: true).trim()
                    }
                }
            }
        }

        stage('Deploy with Ansible') {
            steps {
                script {
                    env.ANSIBLE_OUTPUT = sh(script: 'ansible-playbook -i ansible/hosts.ini ansible/deploy.yml', returnStdout: true).trim()
                }
            }
        }

        stage('Verify Deployment') {
            steps {
                script {
                    env.VERIFY_OUTPUT = sh(script: 'docker ps | grep scientific-calculator || echo "Container not running"', returnStdout: true).trim()
                }
            }
        }
    }

    post {
        success {
            mail to: 'Tanish.Pathania@iiitb.ac.in',
                 subject: "✅ Build SUCCESS: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                 body: """\
                            The Jenkins pipeline completed successfully.

                            Job: ${env.JOB_NAME}
                            Build: #${env.BUILD_NUMBER}

                            --- Test Output ---
                            ${env.TEST_OUTPUT}

                            --- Package Output ---
                            ${env.PACKAGE_OUTPUT}

                            --- Docker Pull Output ---
                            ${env.DOCKER_PULL_OUTPUT}

                            --- Docker Build Output ---
                            ${env.DOCKER_BUILD_OUTPUT}

                            --- Docker Push Output ---
                            ${env.DOCKER_PUSH_OUTPUT}

                            --- Ansible Deployment Output ---
                            ${env.ANSIBLE_OUTPUT}

                            --- Deployment Verification ---
                            ${env.VERIFY_OUTPUT}
                            """
        }
        failure {
            mail to: 'Tanish.Pathania@iiitb.ac.in',
                 subject: "❌ Build FAILURE: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                 body: """\
                            The Jenkins pipeline failed.

                            Job: ${env.JOB_NAME}
                            Build: #${env.BUILD_NUMBER}

                            --- Test Output ---
                            ${env.TEST_OUTPUT}

                            --- Package Output ---
                            ${env.PACKAGE_OUTPUT}

                            --- Docker Pull Output ---
                            ${env.DOCKER_PULL_OUTPUT}

                            --- Docker Build Output ---
                            ${env.DOCKER_BUILD_OUTPUT}

                            --- Docker Push Output ---
                            ${env.DOCKER_PUSH_OUTPUT}

                            --- Ansible Deployment Output ---
                            ${env.ANSIBLE_OUTPUT}

                            --- Deployment Verification ---
                            ${env.VERIFY_OUTPUT}
                            """
        }
        always {
            cleanWs()
            echo "Pipeline run over."
        }
    }
}
