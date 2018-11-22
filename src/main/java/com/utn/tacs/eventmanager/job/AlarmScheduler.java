package com.utn.tacs.eventmanager.job;

import com.utn.tacs.eventmanager.dao.Alarm;
import com.utn.tacs.eventmanager.dao.Event;
import com.utn.tacs.eventmanager.errors.CustomException;
import com.utn.tacs.eventmanager.services.AlarmService;
import com.utn.tacs.eventmanager.services.EventService;
import com.utn.tacs.eventmanager.services.EventbriteService;
import com.utn.tacs.eventmanager.services.dto.EventsResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;

@Component
public class AlarmScheduler {

	private static final String EVERYDAY_AFTERNOON = "0 0 12 * * ?";

	@Autowired
	private AlarmService alarmService;

	@Autowired
	private EventbriteService eventbriteService;

	@Autowired
	private EventService eventService;

	/**
	 * This job will add 20 interesting events for the user everyday, to be shown
	 * on the web_app frontend and as welcome message on Telegram.
	 */
	@Scheduled(cron = EVERYDAY_AFTERNOON)
	public final void activateAlarms() {

		eventService.clearEvents();

		List<Alarm> alarms = alarmService.getAlarms();

		for (Alarm alarm : alarms) {
			try {
				EventsResponseDTO events = eventbriteService.getEvents("1", alarm.getCriteria());

				events.getEvents().subList(0, 20).forEach((map) -> eventService.addEvent(new Event(Long.valueOf(map.get("id").toString()), alarm.getUserId(), alarm.getId())));

			} catch (CustomException e) {
				e.printStackTrace();
			}
		}
	}

}
