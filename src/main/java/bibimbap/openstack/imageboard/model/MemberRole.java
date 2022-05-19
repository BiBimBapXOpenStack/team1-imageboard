package bibimbap.openstack.imageboard.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberRole {
    ROLE_ADMIN(0),
    ROLE_USER(1);

    int index;

    public static String valueOf(int index) {
        for (MemberRole memberRole : MemberRole.values()) {
            if (index == memberRole.index)
                return memberRole.name();
        }
        return null;
    }
}
