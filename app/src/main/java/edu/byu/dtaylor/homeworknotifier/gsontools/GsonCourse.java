
package edu.byu.dtaylor.homeworknotifier.gsontools;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

public class GsonCourse {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("shortTitle")
    @Expose
    private String shortTitle;
    @SerializedName("assignments")
    @Expose
    private List<GsonAssignment> assignments = new ArrayList<GsonAssignment>();

    /**
     * 
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(String id) {
        this.id = id;
    }

    public GsonCourse withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * 
     * @return
     *     The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    public GsonCourse withTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * 
     * @return
     *     The shortTitle
     */
    public String getShortTitle() {
        return shortTitle;
    }

    /**
     * 
     * @param shortTitle
     *     The shortTitle
     */
    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    public GsonCourse withShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
        return this;
    }

    /**
     * 
     * @return
     *     The assignments
     */
    public List<GsonAssignment> getAssignments() {
        return assignments;
    }

    /**
     * 
     * @param assignments
     *     The assignments
     */
    public void setAssignments(List<GsonAssignment> assignments) {
        this.assignments = assignments;
    }

    public GsonCourse withAssignments(List<GsonAssignment> assignments) {
        this.assignments = assignments;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(title).append(shortTitle).append(assignments).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof GsonCourse) == false) {
            return false;
        }
        GsonCourse rhs = ((GsonCourse) other);
        return new EqualsBuilder().append(id, rhs.id).append(title, rhs.title).append(shortTitle, rhs.shortTitle).append(assignments, rhs.assignments).isEquals();
    }

}
