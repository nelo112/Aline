package de.fh.rosenheim.aline.model.dtos.booking;

import de.fh.rosenheim.aline.model.domain.Booking;
import de.fh.rosenheim.aline.model.dtos.seminar.SeminarFactory;
import de.fh.rosenheim.aline.model.dtos.user.UserFactory;
import de.fh.rosenheim.aline.util.SeminarUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Manuel on 02.01.2017.
 */
public class BookingFactory {

    public static BookingDTO toBookingDTO(Booking booking) {
        BookingDTO dto = new BookingDTO();
        dto.setId(booking.getId());
        dto.setCreated(booking.getCreated());
        dto.setUpdated(booking.getUpdated());
        dto.setStatus(booking.getStatus());

        dto.setSeminar(SeminarFactory.toSeminarDTO(booking.getSeminar()));

        dto.setUser(UserFactory.toUserDTO(booking.getUser()));

        return dto;
    }

    public static UserBookingDTO toUserBookingDTO(Booking booking) {
        UserBookingDTO userBookingDTO = new UserBookingDTO();

        userBookingDTO.setId(booking.getId());
        userBookingDTO.setStatus(booking.getStatus());
        userBookingDTO.setCreated(booking.getCreated());
        userBookingDTO.setUpdated(booking.getUpdated());
        userBookingDTO.setSeminarId(booking.getSeminar().getId());
        userBookingDTO.setSeminarName(booking.getSeminar().getName());
        userBookingDTO.setSeminarCost(booking.getSeminar().getCostsPerParticipant());
        userBookingDTO.setSeminarYear(SeminarUtil.getYear(booking.getSeminar()));

        return userBookingDTO;
    }

    public static List<BookingSummaryDTO> toBookingSummaryDTOs(Collection<Booking> bookings) {
        List<UserBookingDTO> bookingDTOs =
                bookings.stream().map(BookingFactory::toUserBookingDTO).collect(Collectors.toList());

        Map<Integer, List<UserBookingDTO>> sortedBookings =
                bookingDTOs.stream().collect(Collectors.groupingBy(UserBookingDTO::getSeminarYear));

        List<BookingSummaryDTO> bookingSummaries = new ArrayList<>();

        sortedBookings.forEach((year, userBookingDTOS) -> {
            BookingSummaryDTO summary = new BookingSummaryDTO();
            summary.setYear(year);
            summary.setBookings(userBookingDTOS);
            summary.setTotalSpending(userBookingDTOS.stream().mapToLong(UserBookingDTO::getSeminarCost).sum());
            bookingSummaries.add(summary);
        });
        return bookingSummaries;
    }
}
