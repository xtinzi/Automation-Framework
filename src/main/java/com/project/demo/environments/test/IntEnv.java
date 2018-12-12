package com.project.demo.environments.test;

import com.project.demo.generic.GenericEnv;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class IntEnv implements GenericEnv {
    private String envName ="int";

    @Value("${profile.name}")
    private String profileName;

    public String getEnvName() {
        return envName;
    }
    public void setEnvName(String envName) {
        this.envName = envName;
    }
    public String getProfileName() {
        return profileName;
    }
    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }
    @Override
    public String toString() {
        return "IntEnv [envName=" + envName + ", profileName=" + profileName+ "]";
    }
}
