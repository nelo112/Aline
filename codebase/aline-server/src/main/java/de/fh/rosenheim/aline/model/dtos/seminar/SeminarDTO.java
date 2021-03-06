package de.fh.rosenheim.aline.model.dtos.seminar;

import com.fasterxml.jackson.annotation.JsonView;
import de.fh.rosenheim.aline.model.dtos.json.view.View;
import de.fh.rosenheim.aline.util.SwaggerTexts;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class SeminarDTO extends SeminarBasicsDTO {

    @JsonView({View.SeminarDetailsView.class, View.SeminarIdView.class})
    private long id;

    /**
     * The number of non-denied bookings (meaning all granted and requested bookings)
     */
    @JsonView(View.SeminarDetailsView.class)
    @ApiModelProperty(notes = SwaggerTexts.ACTIVE_BOOKINGS)
    private int activeBookings;

    @JsonView(View.SeminarDetailsView.class)
    private Date created;

    @JsonView(View.SeminarDetailsView.class)
    private boolean billGenerated;

    @JsonView(View.SeminarDetailsView.class)
    private Date updated;
}
