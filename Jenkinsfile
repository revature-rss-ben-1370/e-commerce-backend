node {
  stage('SCM') {
    checkout scm
  }
  stage('SonarQube Analysis') {
    def mvn = tool 'Maven';
    def scanner = tool 'Sonar-Scanner';
    withSonarQubeEnv('sonarserver') {
      sh "${mvn}/bin/mvn sonar:sonar"
      sh "${scanner}/bin/sonar-scanner"
    }
  }
}