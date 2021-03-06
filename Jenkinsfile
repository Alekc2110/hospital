pipeline {
    agent any
    tools{
        maven 'Maven'
    }
    stages {
        stage('unit tests') {
               steps {
                echo "starts running unit testing"
                bat "mvn -pl hospital-main clean compile test"
            }
        }

        stage('integration tests') {
                       steps {
                        echo "starts running integration testing"
                        bat "mvn -pl hospital-main -Pfailsafe verify"
                    }
        }

         stage('hospital build jar') {
                        steps {
                         echo "building project"
                         bat "mvn -pl hospital-main clean package"
                               }
         }

         stage('sonarqube verify') {
                        steps {
                         echo "verify by sonarqube"
 //                      bat "mvn clean verify sonar:sonar -Dsonar.projectKey=hospital -Dsonar.host.url=http://localhost:9000 -Dsonar.login=5517ea47b308f8bd4d9c5d800046fdb80ba026b7"
                         withSonarQubeEnv('SonarQube'){
                         bat "mvn clean verify sonar:sonar"
                         }

                              }
                  }

          stage('sonarqube quality gate') {
                                 steps {
                                  echo "quality gate sonarqube"
                                  waitForQualityGate abortPipeline: true

                                       }
                           }


         stage('build docker image') {
                        steps {
                         echo "building docker image"
                         bat "docker build . -t alekckorsh/hospital"
                              }
         }

          stage('run docker container alekckorsh/hospital') {
                                 steps {
                                  echo "running docker container"
                                  bat "docker run -d --rm -p 8085:8085 --name hospital-petclinic alekckorsh/hospital:latest"
                                       }
                  }

          stage('functional cucumber tests') {
                                 steps {
                                  echo "starts running functional testing"
                                  bat "mvn -pl hospital-functional-tests -DskipTests=false -Dtest=RunnerTest test"

                                 }
                 }


    }
}
