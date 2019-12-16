node {
    pipeline {
    agent any
    stages {
        stage('build') {
            steps {
                  load "pipeline/vars.properties"
                  echo "${environment}"
            }
        }
    }
}
}