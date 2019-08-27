pipeline {
    agent any
    stages {
        stage('build stage') {
            steps {
                withMaven(maven : 'Maven') {
                    sh 'mvn --version'
                }
            }
        }
    }
}
