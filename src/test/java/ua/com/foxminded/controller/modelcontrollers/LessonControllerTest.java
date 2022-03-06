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
    private static final String LESSONS_ATTRIBUTE_NAME = "lessonDTOS";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String TEACHER_FULL_NAME = "teacherFullName";
    private static final String TEACHER_ID = "teacherId";
    private static final String GROUP_ABBREVIATION = "groupAbbreviation";
    private static final String GROUP_ID = "groupId";
    private static final String DATE_TIME = "dateTime";
    private static final String CLASS_NUMBER = "classNumber";
    private static final String CLASS_ROOM_ID = "classRoomId";
    private static final String SLASH = "/";

    private final LocalDateTime randomLocalDateTime = DateUtils.getLocalDateTimeFromString("15.03.2022 15:30");
    private final List<Teacher> randomTeachers = Arrays.asList(
        new Teacher(3L, 3L, "Joe", "Weelson", 56),
        new Teacher(4L, 4L, "Jack", "Trump", 60)
    );
    private final List<Group> randomGroups = Arrays.asList(
        new Group(1L, 2L, 3L, 4),
        new Group(2L, 5L, 6L, 7)
    );
    private final List<ClassRoom> randomClassRooms = Arrays.asList(
        new ClassRoom(1L, "1"),
        new ClassRoom(2L, "2")
    );

    private final List<Lesson> randomLessons = Arrays.asList(
        new Lesson(1L, "Math",
            randomTeachers.get(0).getId(),
            randomGroups.get(0).getId(),
            randomLocalDateTime,
            randomClassRooms.get(0).getId()),
        new Lesson(2L, "Chemists",
            randomTeachers.get(1).getId(),
            randomGroups.get(1).getId(),
            randomLocalDateTime,
            randomClassRooms.get(1).getId())
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
        when(teacherService.getById(randomLessons.get(0).getTeacherId())).thenReturn(randomTeachers.get(0));
        when(groupService.getById(randomLessons.get(0).getGroupId())).thenReturn(randomGroups.get(0));
        when(classRoomService.getById(randomLessons.get(0).getClassRoomId())).thenReturn(randomClassRooms.get(0));
        when(teacherService.getById(randomLessons.get(1).getTeacherId())).thenReturn(randomTeachers.get(1));
        when(groupService.getById(randomLessons.get(1).getGroupId())).thenReturn(randomGroups.get(1));
        when(classRoomService.getById(randomLessons.get(1).getClassRoomId())).thenReturn(randomClassRooms.get(1));

        mockMvc.perform(get(TEMPLATE_URL))
            .andExpect(status().isOk())
            .andExpect(view().name(VIEW_FOLDER_NAME + INDEX_VIEW))
            .andExpect(model().attribute(LESSONS_ATTRIBUTE_NAME, hasSize(randomLessons.size())))
            .andExpect(model().attribute(LESSONS_ATTRIBUTE_NAME, hasItem(
                allOf(
                    hasProperty(ID, is(randomLessons.get(0).getId())),
                    hasProperty(NAME, is(randomLessons.get(0).getName())),
                    hasProperty(TEACHER_FULL_NAME, is(randomTeachers.get(0).getFirstName() + " " + randomTeachers.get(0).getLastName())),
                    hasProperty(GROUP_ABBREVIATION, is(randomGroups.get(0).getAbbreviation())),
                    hasProperty(CLASS_NUMBER, is(randomClassRooms.get(0).getClassNumber()))
                )
            )))
            .andExpect(model().attribute(LESSONS_ATTRIBUTE_NAME, hasItem(
                allOf(
                    hasProperty(ID, is(randomLessons.get(1).getId())),
                    hasProperty(NAME, is(randomLessons.get(1).getName())),
                    hasProperty(TEACHER_FULL_NAME, is(randomTeachers.get(1).getFirstName() + " " + randomTeachers.get(1).getLastName())),
                    hasProperty(GROUP_ABBREVIATION, is(randomGroups.get(1).getAbbreviation())),
                    hasProperty(CLASS_NUMBER, is(randomClassRooms.get(1).getClassNumber())))
            )));

        verify(lessonService, times(2)).getAll();
        verifyNoMoreInteractions(lessonService);
    }

    @Test
    void newEntity_shouldReturnNewEntityView() throws Exception {

        mockMvc.perform(get(TEMPLATE_URL + NEW_VIEW))
            .andExpect(status().isOk())
            .andExpect(view().name(VIEW_FOLDER_NAME + EDIT_VIEW))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(ID, nullValue())))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(TEACHER_ID, nullValue())))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(GROUP_ID, nullValue())))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(CLASS_ROOM_ID, nullValue())));
    }

    @Test
    void edit_shouldReturnLessonsEditView() throws Exception {

        when(lessonService.getById(randomLesson.getId())).thenReturn(randomLesson);
        when(groupService.getAll()).thenReturn(randomGroups);
        when(teacherService.getAll()).thenReturn(randomTeachers);
        when(classRoomService.getAll()).thenReturn(randomClassRooms);

        mockMvc.perform(get(TEMPLATE_URL + SLASH + randomLesson.getId() + EDIT_VIEW)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
            .andExpect(status().isOk())
            .andExpect(view().name(VIEW_FOLDER_NAME + EDIT_VIEW))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(ID, is(randomLesson.getId()))))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(TEACHER_ID, is(randomLesson.getTeacherId()))))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(GROUP_ID, is(randomLesson.getGroupId()))))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(CLASS_ROOM_ID, is(randomLesson.getClassRoomId()))));

        verify(lessonService, times(1)).getById(randomLesson.getId());
        verifyNoMoreInteractions(lessonService);
    }

    @Test
    void create_shouldCreateLessonAndRedirect() throws Exception {

        when(lessonService.create(new Lesson(
            randomLesson.getName(),
            randomLesson.getTeacherId(),
            randomLesson.getGroupId(),
            randomLocalDateTime,
            randomLesson.getClassRoomId()))).thenReturn(randomLesson);

        mockMvc.perform(post(TEMPLATE_URL)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param(NAME, randomLesson.getName())
                .param(TEACHER_ID, String.valueOf(randomLesson.getTeacherId()))
                .param(GROUP_ID, String.valueOf(randomLesson.getGroupId()))
                .param(DATE_TIME, "15.03.2022 15:30")
                .param(CLASS_ROOM_ID, String.valueOf(randomLesson.getClassRoomId())))
            .andExpect(status().is3xxRedirection())
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(ID, is(randomLesson.getId()))))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(NAME, is(randomLesson.getName()))))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(TEACHER_ID, is(randomLesson.getTeacherId()))))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(GROUP_ID, is(randomLesson.getGroupId()))))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(CLASS_ROOM_ID, is(randomLesson.getClassRoomId()))));

        verify(lessonService, times(1)).create(new Lesson(
            "Math",
            randomLesson.getTeacherId(),
            randomLesson.getGroupId(),
            randomLocalDateTime,
            randomLesson.getClassRoomId()));

        verifyNoMoreInteractions(lessonService);
    }

    @Test
    void update_shouldUpdateLessonAndRedirect() throws Exception {

        String randomName = "Biology";
        long randomTeacherId = 6L;
        long randomGroupId = 9L;
        String randomDateTime = "12.01.2020 09:25";
        long randomClassroomId = 3L;

        when(lessonService.getById(randomLesson.getId())).thenReturn(new Lesson(
            randomLesson.getId(),
            randomName,
            randomTeacherId,
            randomGroupId,
            DateUtils.getLocalDateTimeFromString(randomDateTime),
            randomClassroomId
        ));

        mockMvc.perform(put(TEMPLATE_URL + SLASH + randomLesson.getId())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param(NAME, randomName)
                .param(TEACHER_ID, String.valueOf(randomTeacherId))
                .param(GROUP_ID, String.valueOf(randomGroupId))
                .param(DATE_TIME, randomDateTime)
                .param(CLASS_ROOM_ID, String.valueOf(randomClassroomId)))
            .andExpect(status().is3xxRedirection())
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(ID, is(randomLesson.getId()))))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(NAME, is(randomName))))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(TEACHER_ID, is(randomTeacherId))))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(GROUP_ID, is(randomGroupId))))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(DATE_TIME, is(DateUtils.getLocalDateTimeFromString(randomDateTime)))))
            .andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(CLASS_ROOM_ID, is(randomClassroomId))));

        verify(lessonService, times(1)).update(randomLesson.getId(), new Lesson(randomName, randomTeacherId, randomGroupId, DateUtils.getLocalDateTimeFromString(randomDateTime), randomClassroomId));
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
