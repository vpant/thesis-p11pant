pipeline{
    agent any
    tools {
        jdk "JDK 8"
        gradle 'Gradle 5.0'
    }

    stages{
        stage("Build"){
            steps{
                configFileProvider([configFile(fileId: 'hibernate-cfg-twittercitymod', targetLocation: 'src/main/resources/assets/tc/hibernate.cfg.xml')]) {}  
                sh 'gradle clean build'
            }
        }
    

        stage("Deploy"){
            steps{
                withCredentials([sshUserPrivateKey(credentialsId: 'okeanos-server-ssh', keyFileVariable: 'keyfile', passphraseVariable: '', usernameVariable: 'username')]) {
                    sh "scp -i ${keyfile} build/libs/twittercity-1.0.jar ${username}:twittercity.vasilispantelis.tech"
                }
            }
        }
    }
    post{
        success{
            slackSend message: 'Twitter City Mod build was successful'
        }
        failure {
            slackSend message: 'Twitter City Mod build failed'
        }
    }
}