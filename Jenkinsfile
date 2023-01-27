pipeline {
    agent any
    tools {
        maven 'Maven 3.8.7'
        jdk 'jdk11'
    }
    triggers {
        pollSCM '* * * * *'
    }
    stages {
        stage ('Initialize') {
            steps {
                sh '''
                    echo "PATH = ${PATH}"
                    echo "M2_HOME = ${M2_HOME}"
                '''
            }
        }
        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }
        stage('Test') {
            steps {
                sh 'echo test'
            }
        }
        stage('Build Docker Image') {
            steps {
                sh 'echo docker'
            }
        }
        stage('Run Docker Image') {
            steps {
                sh 'echo dockerRun'
            }
        }
    }
}