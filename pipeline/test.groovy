node {

  stage('Preparation') {
    sh 'pwd'
    sh 'pwd'
    sh 'ls'
    sh 'pwd'
    properties = readProperties file: 'scripts/jenkins-pipelines/branch-specific.properties'
    echo "Running build ${JOB_NAME}"

  }

}