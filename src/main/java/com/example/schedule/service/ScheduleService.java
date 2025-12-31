package com.example.schedule.service;

import com.example.schedule.dto.*;
import com.example.schedule.entity.Schedule;
import com.example.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Transactional
    public ScheduleCreateResponse create(ScheduleCreateRequest request) {
        Schedule schedule = new Schedule((request.getTitle()));
        Schedule savedSchedule = scheduleRepository.save(schedule);
        return new ScheduleCreateResponse(savedSchedule.getId(), savedSchedule.getTitle(), savedSchedule.getCreatedAt(), savedSchedule.getModifiedAt());
    }

    @Transactional(readOnly = true)
    public List<ScheduleGetResponse> findAll() {
        List<Schedule> schedules = scheduleRepository.findAll();
        return schedules.stream().map(schedule -> new ScheduleGetResponse(schedule.getId(), schedule.getTitle(), schedule.getCreatedAt(), schedule.getModifiedAt())).toList();
    }

    @Transactional(readOnly = true)
    public ScheduleGetResponse findOne(Long scheduleId) {
       Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new IllegalStateException("Schedule not found"));
       return new ScheduleGetResponse(schedule.getId(), schedule.getTitle(), schedule.getCreatedAt(), schedule.getModifiedAt());
    }

    @Transactional
    public ScheduleUpdateResponse update(Long scheduleId, ScheduleUpdateRequest request) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(() -> new IllegalStateException("Schedule not found"));
        schedule.update(request.getTitle());
        return new ScheduleUpdateResponse(schedule.getId(), schedule.getTitle(), schedule.getCreatedAt(), schedule.getModifiedAt());
    }

    @Transactional
    public void delete(long scheduleId) {
        boolean existence = scheduleRepository.existsById(scheduleId);
        if (!existence) {
            throw new IllegalStateException("Schedule not found");
        }
        scheduleRepository.deleteById(scheduleId);
    }
}
