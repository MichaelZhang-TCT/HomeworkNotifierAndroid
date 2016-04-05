
package edu.byu.dtaylor.homeworknotifier.database;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

public class Course {

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
    private List<Assignment> assignments = new ArrayList<Assignment>();

    public Course(String courseId, String courseTitle, String courseShortTitle) {
        this.id = courseId;
        this.title = courseTitle;
        this.shortTitle = courseShortTitle;
    }

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

    public Course withId(String id) {
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

    public Course withTitle(String title) {
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

    public Course withShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
        return this;
    }

    /**
     * 
     * @return
     *     The assignments
     */
    public List<Assignment> getAssignments() {
        return assignments;
    }

    /**
     * 
     * @param assignments
     *     The assignments
     */
    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
    }

    public Course withAssignments(List<Assignment> assignments) {
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
        if ((other instanceof Course) == false) {
            return false;
        }
        Course rhs = ((Course) other);
        return new EqualsBuilder().append(id, rhs.id).append(title, rhs.title).append(shortTitle, rhs.shortTitle).append(assignments, rhs.assignments).isEquals();
    }

}
