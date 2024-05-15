package com.example.coursemanagementapi.enums;

public enum ApplicationUserPermissions {
    SUPER_ADMIN_ENABLE("super_admin: enable"),
    SUPER_ADMIN_DISABLE("super_admin: disable"),
    SUPER_ADMIN_UPDATE("super_admin: update"),
    ADMIN_SCHEDULE("admin: schedule"),
    ADMIN_REPORT("admin: reports"),
    USER_COURSE_CONTENT("user_course_content: course_content"),
    USER_RANKING("user_ranking: ranking")
    ;
    //Created the permissions for every User Role to determine all permissions and privileges for each role.
    private final String permission ;
    ApplicationUserPermissions(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
