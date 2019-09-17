package eu.wiegandt.openworkshoporganizer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;

import static org.hibernate.annotations.CascadeType.ALL;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpeningTimes extends PanacheEntityBase {
  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  private UUID id;

  @NotNull
  private String name;
  private String description;
  private LocalTime timeFrom;
  private LocalTime timeTo;

  @ElementCollection
  @Cascade(value = {ALL})
  private Collection<DayOfWeek> daysOfWeek;

  /**
   * DO NOT USE! ONLY FOR JPA!
   */
  OpeningTimes() {
    super();
    name = "";
  }

  @JsonCreator
  public OpeningTimes(
      @JsonProperty("name") String name,
      @JsonProperty("timeFrom") LocalTime timeFrom,
      @JsonProperty("timeTo") LocalTime timeTo,
      @JsonProperty("daysOfWeek") Collection<DayOfWeek> daysOfWeek) {
    this.name = name;
    this.timeFrom = timeFrom;
    this.timeTo = timeTo;
    this.daysOfWeek = new HashSet<>(daysOfWeek);
  }

  public OpeningTimes(String name, LocalTime from, LocalTime to, DayOfWeek... daysOfWeek) {
    this(name, from, to, new ArrayList<>(Arrays.asList(daysOfWeek)));
  }

  public LocalTime getTimeFrom() {
    return timeFrom;
  }

  public void setTimeFrom(LocalTime timeFrom) {
    this.timeFrom = timeFrom;
  }

  public LocalTime getTimeTo() {
    return timeTo;
  }

  public void setTimeTo(LocalTime timeTo) {
    this.timeTo = timeTo;
  }

  public Collection<DayOfWeek> getDaysOfWeek() {
    return daysOfWeek;
  }

  public void setDaysOfWeek(Set<DayOfWeek> daysOfWeek) {
    this.daysOfWeek = daysOfWeek;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof OpeningTimes)) return false;
    OpeningTimes that = (OpeningTimes) o;
    return Objects.equals(getId(), that.getId())
            && Objects.equals(getName(), that.getName())
            && Objects.equals(getDescription(), that.getDescription())
            && Objects.equals(getTimeFrom(), that.getTimeFrom())
            && Objects.equals(getTimeTo(), that.getTimeTo())
            && Objects.equals(getDaysOfWeek(), that.getDaysOfWeek());
  }

  @Override
  public int hashCode() {
    return Objects.hash(
            getId(), getName(), getDescription(), getTimeFrom(), getTimeTo(), getDaysOfWeek());
  }

  @Override
  public String toString() {
    return "OpeningTimes{"
            + "id="
            + id
            + ", name='"
            + name
            + '\''
            + ", description='"
            + description
            + '\''
            + ", timeFrom="
            + timeFrom
            + ", timeTo="
            + timeTo
            + ", daysOfWeek="
            + daysOfWeek
            + '}';
  }

  public void merge(OpeningTimes openingTimes) {
    this.name = openingTimes.name;
    this.description = openingTimes.description;
    this.timeFrom = openingTimes.timeFrom;
    this.timeTo = openingTimes.timeTo;
    this.daysOfWeek = openingTimes.daysOfWeek;
  }
}
