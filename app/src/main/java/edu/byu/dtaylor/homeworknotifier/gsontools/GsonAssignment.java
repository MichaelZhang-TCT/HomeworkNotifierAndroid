
package edu.byu.dtaylor.homeworknotifier.gsontools;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class GsonAssignment {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("categoryID")
    @Expose
    private String categoryID;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("courseID")
    @Expose
    private String courseID;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("dueDate")
    @Expose
    private Integer dueDate;
    @SerializedName("graded")
    @Expose
    private Boolean graded;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("points")
    @Expose
    private Integer points;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("weight")
    @Expose
    private Double weight;

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

    public GsonAssignment withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * 
     * @return
     *     The categoryID
     */
    public String getCategoryID() {
        return categoryID;
    }

    /**
     * 
     * @param categoryID
     *     The categoryID
     */
    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public GsonAssignment withCategoryID(String categoryID) {
        this.categoryID = categoryID;
        return this;
    }

    /**
     * 
     * @return
     *     The category
     */
    public String getCategory() {
        return category;
    }

    /**
     * 
     * @param category
     *     The category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    public GsonAssignment withCategory(String category) {
        this.category = category;
        return this;
    }

    /**
     * 
     * @return
     *     The courseID
     */
    public String getCourseID() {
        return courseID;
    }

    /**
     * 
     * @param courseID
     *     The courseID
     */
    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public GsonAssignment withCourseID(String courseID) {
        this.courseID = courseID;
        return this;
    }

    /**
     * 
     * @return
     *     The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * 
     * @param description
     *     The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public GsonAssignment withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * 
     * @return
     *     The dueDate
     */
    public Integer getDueDate() {
        return dueDate;
    }

    /**
     * 
     * @param dueDate
     *     The dueDate
     */
    public void setDueDate(Integer dueDate) {
        this.dueDate = dueDate;
    }

    public GsonAssignment withDueDate(Integer dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    /**
     * 
     * @return
     *     The graded
     */
    public Boolean getGraded() {
        return graded;
    }

    /**
     * 
     * @param graded
     *     The graded
     */
    public void setGraded(Boolean graded) {
        this.graded = graded;
    }

    public GsonAssignment withGraded(Boolean graded) {
        this.graded = graded;
        return this;
    }

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    public GsonAssignment withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * 
     * @return
     *     The points
     */
    public Integer getPoints() {
        return points;
    }

    /**
     * 
     * @param points
     *     The points
     */
    public void setPoints(Integer points) {
        this.points = points;
    }

    public GsonAssignment withPoints(Integer points) {
        this.points = points;
        return this;
    }

    /**
     * 
     * @return
     *     The type
     */
    public String getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    public void setType(String type) {
        this.type = type;
    }

    public GsonAssignment withType(String type) {
        this.type = type;
        return this;
    }

    /**
     * 
     * @return
     *     The url
     */
    public String getUrl() {
        return url;
    }

    /**
     * 
     * @param url
     *     The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    public GsonAssignment withUrl(String url) {
        this.url = url;
        return this;
    }

    /**
     * 
     * @return
     *     The weight
     */
    public Double getWeight() {
        return weight;
    }

    /**
     * 
     * @param weight
     *     The weight
     */
    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public GsonAssignment withWeight(Double weight) {
        this.weight = weight;
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).append(categoryID).append(category).append(courseID).append(description).append(dueDate).append(graded).append(name).append(points).append(type).append(url).append(weight).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof GsonAssignment) == false) {
            return false;
        }
        GsonAssignment rhs = ((GsonAssignment) other);
        return new EqualsBuilder().append(id, rhs.id).append(categoryID, rhs.categoryID).append(category, rhs.category).append(courseID, rhs.courseID).append(description, rhs.description).append(dueDate, rhs.dueDate).append(graded, rhs.graded).append(name, rhs.name).append(points, rhs.points).append(type, rhs.type).append(url, rhs.url).append(weight, rhs.weight).isEquals();
    }

}
