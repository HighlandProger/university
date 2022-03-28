package ua.com.foxminded.dto;

import ua.com.foxminded.model.ClassRoom;
import ua.com.foxminded.model.Group;
import ua.com.foxminded.model.Lesson;
import ua.com.foxminded.model.Teacher;
import ua.com.foxminded.service.AbstractService;
import ua.com.foxminded.utils.DateUtils;

import java.time.LocalDateTime;

/**
 * DTO class LessonDTO.
 */
public class LessonDTO {

    /**
     * Property - id
     */
    private Long id;
    /**
     * Property - name
     */
    private String name;
    /**
     * Property - group id
     */
    private Long groupId;
    /**
     * Property - teacher id
     */
    private Long teacherId;
    /**
     * Property - date time
     */
    private String dateTime;
    /**
     * Property - class room id
     */
    private Long classRoomId;

    /**
     * Empty constructor
     */
    public LessonDTO() {
    }

    /**
     * Constructor based on model Lesson
     *
     * @param lesson lesson with already defined properties
     * @see DateUtils#getStringFromLocalDateTime(LocalDateTime)
     */
    public LessonDTO(Lesson lesson) {

        this.id = lesson.getId();
        this.name = lesson.getName();

        if (lesson.getDateTime() != null) {
            this.dateTime = DateUtils.getStringFromLocalDateTime(lesson.getDateTime());
        }

        if (lesson.getGroup() != null) {
            this.groupId = lesson.getGroup().getId();
        }

        if (lesson.getTeacher() != null) {
            this.teacherId = lesson.getTeacher().getId();
        }

        if (lesson.getClassRoom() != null) {
            this.classRoomId = lesson.getClassRoom().getId();
        }
    }

    /**
     * Returns a Lesson object with defined properties.
     *
     * <p>Firstly creates a new Lesson object and sets its id and name propeties.
     * After, using DateUtils class converts this String date time to LocalDateTime object and sets it.
     *
     * <p>GroupService searches for the group object by this groupId property
     * to set it for the Lesson object.
     *
     * <p>TeacherService searches for the teacher object by this teacherId property
     * to set it for the Lesson object.
     *
     * @param groupService     service for searching group by id in groups table
     * @param teacherService   service for searching teacher by id in teachers table
     * @param classRoomService service for searching class room by id in classrooms table
     * @return a Lesson object with defined properties
     * @see DateUtils#getLocalDateTimeFromString(String)
     * @see ua.com.foxminded.service.GroupService#getById(Long)
     * @see ua.com.foxminded.service.TeacherService#getById(Long)
     * @see ua.com.foxminded.service.ClassRoomService#getById(Long)
     */
    public Lesson getLesson(AbstractService<Group> groupService,
                            AbstractService<Teacher> teacherService,
                            AbstractService<ClassRoom> classRoomService) {

        Lesson lesson = new Lesson();
        lesson.setId(this.id);
        lesson.setName(this.name);
        lesson.setDateTime(DateUtils.getLocalDateTimeFromString(this.dateTime));
        lesson.setGroup(groupService.getById(this.groupId));
        lesson.setTeacher(teacherService.getById(this.teacherId));
        lesson.setClassRoom(classRoomService.getById(this.classRoomId));

        return lesson;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public Long getClassRoomId() {
        return classRoomId;
    }

    public void setClassRoomId(Long classRoomId) {
        this.classRoomId = classRoomId;
    }
}
