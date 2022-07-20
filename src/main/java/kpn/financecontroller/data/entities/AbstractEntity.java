// TODO: 19.07.2022 del
//package kpn.financecontroller.data.entities;
//
//import lombok.Getter;
//import lombok.Setter;
//
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.MappedSuperclass;
//
//@MappedSuperclass
//@Getter
//@Setter
//abstract public class AbstractEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    protected Long id;
//
//    @Override
//    public boolean equals(Object o) {
//        if (!(o instanceof AbstractEntity)){
//            return false;
//        }
//
//        AbstractEntity other = (AbstractEntity) o;
//        return other.id != null ? id.equals(other.id) : super.equals(other);
//    }
//
//    @Override
//    public int hashCode() {
//        return id != null ? id.hashCode() : super.hashCode();
//    }
//}
