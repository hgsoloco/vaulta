node {

  stage('Preparation') {
    sh 'pwd'
    properties = readProperties file: 'scripts/jenkins-pipelines/branch-specific.properties'
    echo "Running build ${JOB_NAME}"

  }

}