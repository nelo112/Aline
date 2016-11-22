package de.fh.rosenheim.aline.model.domain;

import de.fh.rosenheim.aline.model.base.DomainBase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * Created by Manuel on 22.11.2016.
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class SeminarBasics extends DomainBase {

    private String name;
    private String description;
    private String trainer;
    private String agenda;
    private boolean bookable;
    private String targetAudiance;
    private String requirements;
    private String reporting;
    private String contactPerson;
    private String trainingType;
    private int maximumParticipants;
    private int costsPerParticipant;
    private String bookingTimelog;
    private String planedAdvancement;
    private int duration;
    private String regularCycle;
    private String dates;
    private Date creationDate;

    public void copyBasics(SeminarBasics newData) {
        name = newData.getName();
        description = newData.getDescription();
        trainer = newData.getTrainer();
        agenda = newData.getAgenda();
        bookable = newData.isBookable();
        targetAudiance = newData.getTargetAudiance();
        requirements = newData.getRequirements();
        reporting = newData.getReporting();
        contactPerson = newData.getContactPerson();
        trainingType = newData.getTrainingType();
        maximumParticipants = newData.getMaximumParticipants();
        costsPerParticipant = newData.getCostsPerParticipant();
        bookingTimelog = newData.getBookingTimelog();
        planedAdvancement = newData.getPlanedAdvancement();
        duration = newData.getDuration();
        regularCycle = newData.getRegularCycle();
        dates = newData.getDates();
        creationDate = newData.getCreationDate();
    }
}