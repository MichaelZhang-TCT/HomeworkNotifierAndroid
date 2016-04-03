
package edu.byu.dtaylor.homeworknotifier.gsontools;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class GsonDatabase {

    @SerializedName("user")
    @Expose
    private GsonUser user;

    /**
     * 
     * @return
     *     The user
     */
    public GsonUser getUser() {
        return user;
    }

    /**
     * 
     * @param user
     *     The user
     */
    public void setUser(GsonUser user) {
        this.user = user;
    }

    public GsonDatabase withUser(GsonUser user) {
        this.user = user;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(user).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof GsonDatabase) == false) {
            return false;
        }
        GsonDatabase rhs = ((GsonDatabase) other);
        return new EqualsBuilder().append(user, rhs.user).isEquals();
    }

}
