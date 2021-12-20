#!groovy
println('------------------------------------------------------------------Import Job CD/terraform-destroy')
def pipelineScript = new File('/var/jenkins_config/jobs/terraform-destroy-pipeline.groovy').getText("UTF-8")

pipelineJob('CD/terraform-destroy') {
    description("Job to destroy AWS instance")

    definition {
        cps {
            script(pipelineScript)
            sandbox()
        }
    }
}