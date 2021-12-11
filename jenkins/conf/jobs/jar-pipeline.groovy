#!groovy

pipeline {
    agent any
    tools {
        maven 'maven'
    }
    environment {
        PROJET = 'projet_devops'
    }
    options {
        buildDiscarder(logRotator(numToKeepStr: '100'))
        ansiColor('xterm')
    }
    stages {
        stage('Job description') {
            steps {
                script {
                    println('Env var: ' + env.TEST)
                    sh 'java --version'
                    sh 'mvn --version'
                    sh 'python3 --version'
                    currentBuild.displayName = "#${BUILD_NUMBER} ${params.VERSION}"
                }
            }
        }
        stage('Git : repo setup') {
            steps {
                git branch: "${params.BRANCH}",
                        url: 'https://github.com/Ozz007/sb3t.git'
            }
        }
        stage('Maven Compile') {
            steps {
                sh 'mvn compile'
            }
        }
        stage('Maven Unit test') {
            when {
                expression { params.SKIP_TESTS == false }
            }
            steps {
                sh 'mvn test'
            }
        }
        stage('Maven Package') {
            steps {
                sh 'mvn package'
            }
        }
        stage('Rename jar') {
            steps {
                sh "mv sb3t-ws/target/sb3t-ws-1.0-SNAPSHOT.jar SB3T-${params.VERSION}-${params.VERSION_TYPE}.jar"
            }
        }
        stage('Maven Integ test') {
            when {
                expression { params.SKIP_TESTS == false }
            }

            steps {
                sh 'mvn verify'
            }
        }

    }
}