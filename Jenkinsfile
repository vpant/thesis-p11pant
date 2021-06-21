pipeline{
    agent any
    tools {
        jdk "JDK 8"
        gradle 'Gradle 5.0'
    }

    stages{

        stage("SonarQube analysis") {
            steps{
                withSonarQubeEnv('sonnar-qube-server') { 
                    sh 'gradle sonarqube'
                }
            }
        }

        stage("Build"){
            steps{
                configFileProvider([configFile(fileId: 'hibernate-cfg-twittercitymod', targetLocation: 'src/main/resources/assets/tc/hibernate.cfg.xml')]) {}  
                sh 'gradle clean build'
            }
        }
    

        stage("Deploy"){
            steps{
                withCredentials([sshUserPrivateKey(credentialsId: 'okeanos-server-ssh', keyFileVariable: 'keyfile', passphraseVariable: '', usernameVariable: 'username')]) {
                    sh "scp -i ${keyfile} -o StrictHostKeyChecking=no build/libs/twittercity-1.0.jar ${username}@twittercity.vasilispantelis.tech:/home/${username}/twittercity-services/minecraft-forge-server/data/mods/twittercity-1.0.jar"
                    sh "ssh -i ${keyfile} -o StrictHostKeyChecking=no ${username}@twittercity.vasilispantelis.tech docker exec twitter_city_minecraft_server rcon-cli stop"
                }
            }
        }
    }
}
