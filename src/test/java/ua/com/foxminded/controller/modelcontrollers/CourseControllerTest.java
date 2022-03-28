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
import ua.com.foxminded.model.Course;
import ua.com.foxminded.service.CourseService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebAppConfiguration
class CourseControllerTest {

    private static final String TEMPLATE_URL = "/courses";
    private static final String VIEW_FOLDER_NAME = "courses";
    private static final String INDEX_VIEW = "/index";
    private static final String NEW_VIEW = "/new";
    private static final String EDIT_VIEW = "/edit";
    private static final String ENTITY_ATTRIBUTE_NAME = "entity";
    private static final String ENTITIES_ATTRIBUTE_NAME = "entities";
    private static final String ID = "id";
    private static final String ESTABLISH_YEAR = "establishYear";
    private static final String SLASH = "/";

    private final Course randomCourse = new Course(1L, 2021);
    private final List<Course> randomCourses = Arrays.asList(new Course(1L, 2021), new Course(2L, 2022));

    @Mock
    CourseService courseService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new CourseController(courseService)).build();
    }

    @Test
    void index_shouldReturnCoursesIndexView() throws Exception {

        when(courseService.getAll()).thenReturn(randomCourses);
        mockMvc.perform(get(TEMPLATE_URL)).andExpect(status().isOk()).andExpect(view().name(VIEW_FOLDER_NAME + INDEX_VIEW)).andExpect(model().attribute(ENTITIES_ATTRIBUTE_NAME, hasSize(randomCourses.size()))).andExpect(model().attribute(ENTITIES_ATTRIBUTE_NAME, hasItem(allOf(hasProperty(ID, is(randomCourses.get(0).getId())), hasProperty(ESTABLISH_YEAR, is(randomCourses.get(0).getEstablishYear())))))).andExpect(model().attribute(ENTITIES_ATTRIBUTE_NAME, hasItem(allOf(hasProperty(ID, is(randomCourses.get(randomCourses.size() - 1).getId())), hasProperty(ESTABLISH_YEAR, is(randomCourses.get(randomCourses.size() - 1).getEstablishYear()))))));

        verify(courseService, times(1)).getAll();
        verifyNoMoreInteractions(courseService);
    }

    @Test
    void newEntity_shouldReturnNewEntityView() throws Exception {

        mockMvc.perform(get(TEMPLATE_URL + NEW_VIEW)).andExpect(status().isOk()).andExpect(view().name(VIEW_FOLDER_NAME + EDIT_VIEW)).andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(ID, nullValue()))).andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(ESTABLISH_YEAR, is(0))));
    }

    @Test
    void edit_shouldReturnCoursesEditView() throws Exception {

        when(courseService.getById(randomCourse.getId())).thenReturn(randomCourse);

        mockMvc.perform(get(TEMPLATE_URL + SLASH + randomCourse.getId() + EDIT_VIEW).contentType(MediaType.APPLICATION_FORM_URLENCODED)).andExpect(status().isOk()).andExpect(view().name(VIEW_FOLDER_NAME + EDIT_VIEW)).andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(ID, is(randomCourse.getId())))).andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(ESTABLISH_YEAR, is(randomCourse.getEstablishYear()))));

        verify(courseService, times(1)).getById(randomCourse.getId());
        verifyNoMoreInteractions(courseService);
    }

    @Test
    void create_shouldCreateCourseAndRedirect() throws Exception {

        when(courseService.create(new Course(randomCourse.getEstablishYear()))).thenReturn(randomCourse);

        mockMvc.perform(post(TEMPLATE_URL).contentType(MediaType.APPLICATION_FORM_URLENCODED).param(ESTABLISH_YEAR, String.valueOf(randomCourse.getEstablishYear()))).andExpect(status().is3xxRedirection()).andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(ID, nullValue()))).andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(ESTABLISH_YEAR, is(randomCourse.getEstablishYear()))));

        verify(courseService, times(1)).create(new Course(randomCourse.getEstablishYear()));
        verifyNoMoreInteractions(courseService);
    }

    @Test
    void update_shouldUpdateCourseAndRedirect() throws Exception {

        int randomYear = 3027;

        mockMvc.perform(put(TEMPLATE_URL + SLASH + randomCourse.getId()).contentType(MediaType.APPLICATION_FORM_URLENCODED).param(ESTABLISH_YEAR, String.valueOf(randomYear))).andExpect(status().is3xxRedirection()).andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(ID, is(randomCourse.getId())))).andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(ESTABLISH_YEAR, is(randomYear))));

//        verify(courseService, times(1)).update(randomCourse.getId(), new Course(randomYear));
        verifyNoMoreInteractions(courseService);
    }

    @Test
    void delete_shouldDeleteCourseAndRedirect() throws Exception {

        mockMvc.perform(delete(TEMPLATE_URL + SLASH + randomCourse.getId()).contentType(MediaType.APPLICATION_FORM_URLENCODED).param(ID, String.valueOf(randomCourse.getId()))).andExpect(status().is3xxRedirection());

        verify(courseService, times(1)).delete(randomCourse.getId());
        verifyNoMoreInteractions(courseService);
    }
}
