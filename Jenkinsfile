node {
  stage('SCM') {
    checkout scm
  }
  stage('SonarQube Analysis') {
    def mvn = tool 'Sonar-Scanner';
    withSonarQubeEnv('sonarserver') {
      sh "${mvn}/bin/sonar-runner "
    }
  }
}