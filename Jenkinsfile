pipeline {
    agent any
    triggers {
        pollSCM '* * * * *'
    }
    stages {
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