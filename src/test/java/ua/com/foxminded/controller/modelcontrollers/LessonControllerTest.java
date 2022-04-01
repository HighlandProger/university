package ua.com.foxminded.controller.modelcontrollers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.com.foxminded.model.ClassRoom;
import ua.com.foxminded.model.Group;
import ua.com.foxminded.model.Lesson;
import ua.com.foxminded.model.Teacher;
import ua.com.foxminded.service.ClassRoomService;
import ua.com.foxminded.service.GroupService;
import ua.com.foxminded.service.LessonService;
import ua.com.foxminded.service.TeacherService;
import ua.com.foxminded.utils.DateUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebAppConfiguration
class LessonControllerTest {

    private static final String TEMPLATE_URL = "/lessons";
    private static final String VIEW_FOLDER_NAME = "lessons";
    private static final String INDEX_VIEW = "/index";
    private static final String NEW_VIEW = "/new";
    private static final String EDIT_VIEW = "/edit";
    private static final String ENTITY_ATTRIBUTE_NAME = "entity";
    private static final String ENTITIES_ATTRIBUTE_NAME = "entities";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String TEACHER = "teacher";
    private static final String TEACHER_ID = "teacherId";
    private static final String GROUP = "group";
    private static final String GROUP_ID = "groupId";
    private static final String DATE_TIME = "dateTime";
    private static final String CLASS_ROOM = "classRoom";
    private static final String CLASS_ROOM_ID = "classRoomId";
    private static final String SLASH = "/";

    private final LocalDateTime randomLocalDateTime = DateUtils.getLocalDateTimeFromString("15.03.2022 15:30");
    private final Teacher randomTeacher = new Teacher(1L, "Fred", "Durst", 50, null);
    private final Group randomGroup = new Group(3L, null, null, 0);
    private final ClassRoom randomClassRoom = new ClassRoom(2L, "2");
    private final List<Lesson> randomLessons = Arrays.asList(
        new Lesson(1L, "Math", randomTeacher, randomGroup, randomLocalDateTime, randomClassRoom),
        new Lesson(4L, "Literature", randomTeacher, randomGroup, randomLocalDateTime, randomClassRoom)
    );

    private final Lesson randomLesson = randomLessons.get(0);

    @Mock
    LessonService lessonService;
    @Mock
    TeacherService teacherService;
    @Mock
    GroupService groupService;
    @Mock
    ClassRoomService classRoomService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new LessonController(lessonService, groupService, teacherService, classRoomService)).build();
    }

    @Test
    void index_shouldReturnLessonsIndexView() throws Exception {

        when(lessonService.getAll()).thenReturn(randomLessons);

        mockMvc.perform(get(TEMPLATE_URL))
            .andExpect(status().isOk())
            .andExpect(view().name(VIEW_FOLDER_NAME + INDEX_VIEW))
            .andExpect(model().attribute(ENTITIES_ATTRIBUTE_NAME, hasSize(randomLessons.size())))
            .andExpect(model().attribute(ENTITIES_ATTRIBUTE_NAME, hasItem(
                allOf(
                    hasProperty(ID, is(randomLessons.get(0).getId())),
                    hasProperty(NAME, is(randomLessons.get(0).getName())),
                    hasProperty(TEACHER, is(randomLessons.get(0).getTeacher())),
                    hasProperty(GROUP, is(randomLessons.get(0).getGroup())),
                    hasProperty(CLASS_ROOM, is(randomLessons.get(0).getClassRoom()))
                )
            )))
            .andExpect(model().attribute(ENTITIES_ATTRIBUTE_NAME, hasItem(
                allOf(
                    hasProperty(ID, is(randomLessons.get(1).getId())),
                    hasProperty(NAME, is(randomLessons.get(1).getName())),
                    hasProperty(TEACHER, is(randomLessons.get(1).getTeacher())),
                    hasProperty(GROUP, is(randomLessons.get(1).getGroup())),
                    hasProperty(CLASS_ROOM, is(randomLessons.get(1).getClassRoom())))
            )));

        verify(lessonService, times(1)).getAll();
        verifyNoMoreInteractions(lessonService);
    }

    @Test
    void newEntity_shouldReturnNewEntityView() throws Exception {

        mockMvc.perform(get(TEMPLATE_URL + NEW_VIEW))
            .andExpect(status().isOk())
            .andExpect(view().name(VIEW_FOLDER_NAME + EDIT_VIEW))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(ID, nullValue())))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(NAME, nullValue())))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(TEACHER_ID, nullValue())))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(GROUP_ID, nullValue())))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(CLASS_ROOM_ID, nullValue())));
    }

    @Test
    void edit_shouldReturnLessonsEditView() throws Exception {

        when(lessonService.getById(randomLesson.getId())).thenReturn(randomLesson);

        mockMvc.perform(get(TEMPLATE_URL + SLASH + randomLesson.getId() + EDIT_VIEW)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
            .andExpect(status().isOk())
            .andExpect(view().name(VIEW_FOLDER_NAME + EDIT_VIEW))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(ID, is(randomLesson.getId()))))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(TEACHER_ID, is(randomLesson.getTeacher().getId()))))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(GROUP_ID, is(randomLesson.getGroup().getId()))))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(CLASS_ROOM_ID, is(randomLesson.getClassRoom().getId()))));

        verify(lessonService, times(1)).getById(randomLesson.getId());
        verifyNoMoreInteractions(lessonService);
    }

    @Test
    void create_shouldCreateLessonAndRedirect() throws Exception {

        when(lessonService.create(new Lesson(
            randomLesson.getName(),
            randomLesson.getTeacher(),
            randomLesson.getGroup(),
            randomLesson.getDateTime(),
            randomLesson.getClassRoom()))).thenReturn(randomLesson);

        when(teacherService.getById(randomLesson.getTeacher().getId())).thenReturn(randomLesson.getTeacher());
        when(groupService.getById(randomLesson.getGroup().getId())).thenReturn(randomLesson.getGroup());
        when(classRoomService.getById(randomLesson.getClassRoom().getId())).thenReturn(randomLesson.getClassRoom());

        mockMvc.perform(post(TEMPLATE_URL)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param(NAME, randomLesson.getName())
                .param(TEACHER_ID, String.valueOf(randomLesson.getTeacher().getId()))
                .param(GROUP_ID, String.valueOf(randomLesson.getGroup().getId()))
                .param(DATE_TIME, DateUtils.getStringFromLocalDateTime(randomLesson.getDateTime()))
                .param(CLASS_ROOM_ID, String.valueOf(randomLesson.getClassRoom().getId())))
            .andExpect(status().is3xxRedirection())
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(ID, nullValue())))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(NAME, is(randomLesson.getName()))))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(TEACHER_ID, is(randomLesson.getTeacher().getId()))))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(GROUP_ID, is(randomLesson.getGroup().getId()))))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(DATE_TIME, is(DateUtils.getStringFromLocalDateTime(randomLesson.getDateTime())))))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(CLASS_ROOM_ID, is(randomLesson.getClassRoom().getId()))));

        verify(lessonService, times(1)).create(new Lesson(
            randomLesson.getName(),
            randomLesson.getTeacher(),
            randomLesson.getGroup(),
            randomLocalDateTime,
            randomLesson.getClassRoom()));

        verifyNoMoreInteractions(lessonService);
    }

    @Test
    void update_shouldUpdateLessonAndRedirect() throws Exception {


        String changedName = "Biology";
        Teacher changedTeacher = new Teacher(8L, "Rob", "Williams", 23, null);
        Group changedGroup = new Group(4L, null, null, 2);
        LocalDateTime changedRandomDateTime = DateUtils.getLocalDateTimeFromString("12.01.2020 09:25");
        ClassRoom changedClassRoom = new ClassRoom(7L, "0");

        Lesson changedLesson = new Lesson(
            randomLesson.getId(),
            changedName,
            changedTeacher,
            changedGroup,
            changedRandomDateTime,
            changedClassRoom
        );

        when(teacherService.getById(changedTeacher.getId())).thenReturn(changedTeacher);
        when(groupService.getById(changedGroup.getId())).thenReturn(changedGroup);
        when(classRoomService.getById(changedClassRoom.getId())).thenReturn(changedClassRoom);

        mockMvc.perform(put(TEMPLATE_URL + SLASH + randomLesson.getId())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param(NAME, changedName)
                .param(TEACHER_ID, String.valueOf(changedTeacher.getId()))
                .param(GROUP_ID, String.valueOf(changedGroup.getId()))
                .param(DATE_TIME, DateUtils.getStringFromLocalDateTime(changedRandomDateTime))
                .param(CLASS_ROOM_ID, String.valueOf(changedClassRoom.getId())))
            .andExpect(status().is3xxRedirection())
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(ID, is(randomLesson.getId()))))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(NAME, is(changedName))))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(TEACHER_ID, is(changedTeacher.getId()))))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(GROUP_ID, is(changedGroup.getId()))))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(DATE_TIME, is(DateUtils.getStringFromLocalDateTime(changedRandomDateTime)))))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(CLASS_ROOM_ID, is(changedClassRoom.getId()))));

        verify(lessonService, times(1)).update(changedLesson);
        verifyNoMoreInteractions(lessonService);
    }

    @Test
    void delete_shouldDeleteLessonAndRedirect() throws Exception {

        mockMvc.perform(delete(TEMPLATE_URL + SLASH + randomLesson.getId())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param(ID, String.valueOf(randomLesson.getId())))
            .andExpect(status().is3xxRedirection());

        verify(lessonService, times(1)).delete(randomLesson.getId());
        verifyNoMoreInteractions(lessonService);
    }
}
