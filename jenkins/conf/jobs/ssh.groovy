#!groovy
println('------------------------------------------------------------------Import Job CD/SSH')
def pipelineScript = new File('/var/jenkins_config/jobs/ssh-pipeline.groovy').getText("UTF-8")

pipelineJob('CD/SSH') {
    description("Job ssh pipeline")
    definition {
        cps {
            script(pipelineScript)
            sandbox()
        }
    }
}