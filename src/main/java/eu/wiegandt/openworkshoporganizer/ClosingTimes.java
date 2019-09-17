package eu.wiegandt.openworkshoporganizer;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
public class ClosingTimes extends PanacheEntityBase {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @NotNull
    private String name;
    private String description;
  private LocalDateTime from;
  private LocalDateTime to;

  public ClosingTimes(String name, LocalDateTime from, LocalDateTime to) {

      this.name = name;
    this.from = from;
    this.to = to;
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

  public LocalDateTime getFrom() {
    return from;
  }

  public void setFrom(LocalDateTime from) {
    this.from = from;
  }

  public LocalDateTime getTo() {
    return to;
  }

  public void setTo(LocalDateTime to) {
    this.to = to;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ClosingTimes)) return false;
    ClosingTimes that = (ClosingTimes) o;
      return Objects.equals(getId(), that.getId())
              && Objects.equals(getName(), that.getName())
              && Objects.equals(getDescription(), that.getDescription())
              && Objects.equals(getFrom(), that.getFrom())
              && Objects.equals(getTo(), that.getTo());
  }

  @Override
  public int hashCode() {
      return Objects.hash(getId(), getName(), getDescription(), getFrom(), getTo());
  }

  @Override
  public String toString() {
      return "ClosingTimes{"
              + "id="
              + id
              + ", name='"
              + name
              + '\''
              + ", description='"
              + description
              + '\''
              + ", from="
              + from
              + ", to="
              + to
              + '}';
  }
}
