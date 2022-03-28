//package ua.com.foxminded.controller.modelcontrollers;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import ua.com.foxminded.model.Department;
//import ua.com.foxminded.model.Teacher;
//import ua.com.foxminded.service.DepartmentService;
//import ua.com.foxminded.service.TeacherService;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static org.hamcrest.Matchers.*;
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@ExtendWith(MockitoExtension.class)
//@WebAppConfiguration
//class TeacherControllerTest {
//
//    private static final String TEMPLATE_URL = "/teachers";
//    private static final String VIEW_FOLDER_NAME = "teachers";
//    private static final String INDEX_VIEW = "/index";
//    private static final String NEW_VIEW = "/new";
//    private static final String EDIT_VIEW = "/edit";
//    private static final String ENTITY_ATTRIBUTE_NAME = "entity";
//    private static final String TEACHERS_ATTRIBUTE_NAME = "teacherDTOS";
//    private static final String ID = "id";
//    private static final String FIRST_NAME = "firstName";
//    private static final String LAST_NAME = "lastName";
//    private static final String AGE = "age";
//    private static final String DEPARTMENT_ID = "departmentId";
//    private static final String DEPARTMENT_NAME = "departmentName";
//    private static final String SLASH = "/";
//
//    private final Teacher randomTeacher = new Teacher(1L, new Department(1L, "IT"), "Donald", "Trump", 60);
//    private final Department randomDepartment = new Department(1L, "Politic");
//    private final List<Teacher> randomTeachers = Arrays.asList(new Teacher(1L, new Department(3L, "Economic"), "Robert", "Kiyosaki", 58), new Teacher(2L, new Department(6L, "History"), "Djordan", "Belfort", 67));
//
//    @Mock
//    TeacherService teacherService;
//    @Mock
//    DepartmentService departmentService;
//
//    private MockMvc mockMvc;
//
//    @BeforeEach
//    public void setup() {
//        this.mockMvc = MockMvcBuilders.standaloneSetup(new TeacherController(teacherService, departmentService)).build();
//    }
//
//    @Test
//    void index_shouldReturnTeachersIndexView() throws Exception {
//
//        when(teacherService.getAll()).thenReturn(randomTeachers);
//        when(departmentService.getById(randomTeachers.get(0).getDepartment().getId())).thenReturn(randomDepartment);
//        when(departmentService.getById(randomTeachers.get(1).getDepartment().getId())).thenReturn(randomDepartment);
//
//        mockMvc.perform(get(TEMPLATE_URL)).andExpect(status().isOk()).andExpect(view().name(VIEW_FOLDER_NAME + INDEX_VIEW)).andExpect(model().attribute(TEACHERS_ATTRIBUTE_NAME, hasSize(randomTeachers.size()))).andExpect(model().attribute(TEACHERS_ATTRIBUTE_NAME, hasItem(allOf(hasProperty(ID, is(randomTeachers.get(0).getId())), hasProperty(FIRST_NAME, is(randomTeachers.get(0).getFirstName())), hasProperty(LAST_NAME, is(randomTeachers.get(0).getLastName())), hasProperty(AGE, is(randomTeachers.get(0).getAge())), hasProperty(DEPARTMENT_NAME, is(randomDepartment.getName())))))).andExpect(model().attribute(TEACHERS_ATTRIBUTE_NAME, hasItem(allOf(hasProperty(ID, is(randomTeachers.get(1).getId())), hasProperty(FIRST_NAME, is(randomTeachers.get(1).getFirstName())), hasProperty(LAST_NAME, is(randomTeachers.get(1).getLastName())), hasProperty(AGE, is(randomTeachers.get(1).getAge())), hasProperty(DEPARTMENT_NAME, is(randomDepartment.getName()))))));
//
//        verify(teacherService, times(2)).getAll();
//        verifyNoMoreInteractions(teacherService);
//    }
//
//    @Test
//    void newEntity_shouldReturnNewEntityView() throws Exception {
//
//        mockMvc.perform(get(TEMPLATE_URL + NEW_VIEW)).andExpect(status().isOk()).andExpect(view().name(VIEW_FOLDER_NAME + EDIT_VIEW)).andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(ID, nullValue()))).andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(FIRST_NAME, nullValue()))).andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(LAST_NAME, nullValue()))).andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(AGE, is(0)))).andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(DEPARTMENT_ID, nullValue())));
//    }
//
//    @Test
//    void edit_shouldReturnTeachersEditView() throws Exception {
//
//        when(teacherService.getById(randomTeacher.getId())).thenReturn(randomTeacher);
//
//        mockMvc.perform(get(TEMPLATE_URL + SLASH + randomTeacher.getId() + EDIT_VIEW).contentType(MediaType.APPLICATION_FORM_URLENCODED)).andExpect(status().isOk()).andExpect(view().name(VIEW_FOLDER_NAME + EDIT_VIEW)).andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(ID, is(randomTeacher.getId())))).andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(FIRST_NAME, is(randomTeacher.getFirstName())))).andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(LAST_NAME, is(randomTeacher.getLastName())))).andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(AGE, is(randomTeacher.getAge())))).andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(DEPARTMENT_ID, is(randomTeacher.getDepartment()))));
//
//        verify(teacherService, times(1)).getById(randomTeacher.getId());
//        verifyNoMoreInteractions(teacherService);
//    }
//
//    @Test
//    void create_shouldCreateTeacherAndRedirect() throws Exception {
//
//        when(teacherService.create(new Teacher(randomTeacher.getFirstName(), randomTeacher.getLastName(), randomTeacher.getAge()))).thenReturn(randomTeacher);
//
//        mockMvc.perform(post(TEMPLATE_URL).contentType(MediaType.APPLICATION_FORM_URLENCODED).param(FIRST_NAME, randomTeacher.getFirstName()).param(LAST_NAME, randomTeacher.getLastName()).param(AGE, String.valueOf(randomTeacher.getAge()))).andExpect(status().is3xxRedirection()).andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(ID, nullValue()))).andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(FIRST_NAME, is(randomTeacher.getFirstName())))).andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(LAST_NAME, is(randomTeacher.getLastName())))).andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(AGE, is(randomTeacher.getAge()))));
//
//        verify(teacherService, times(1)).create(new Teacher(randomTeacher.getFirstName(), randomTeacher.getLastName(), randomTeacher.getAge()));
//        verifyNoMoreInteractions(teacherService);
//    }
//
//    @Test
//    void update_shouldUpdateTeacherAndRedirect() throws Exception {
//
//        String randomFirstName = "Bob";
//        String randomLastName = "Jobs";
//        int randomAge = 29;
//
//        mockMvc.perform(put(TEMPLATE_URL + SLASH + randomTeacher.getId()).contentType(MediaType.APPLICATION_FORM_URLENCODED).param(FIRST_NAME, randomFirstName).param(LAST_NAME, randomLastName).param(AGE, String.valueOf(randomAge))).andExpect(status().is3xxRedirection()).andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(ID, is(randomTeacher.getId())))).andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(FIRST_NAME, is(randomFirstName)))).andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(LAST_NAME, is(randomLastName)))).andExpect(model().attribute(ENTITY_ATTRIBUTE_NAME, hasProperty(AGE, is(randomAge))));
//
//        verify(teacherService, times(1)).update(randomTeacher.getId(), new Teacher(randomFirstName, randomLastName, randomAge));
//        verifyNoMoreInteractions(teacherService);
//    }
//
//    @Test
//    void delete_shouldDeleteTeacherAndRedirect() throws Exception {
//
//        mockMvc.perform(delete(TEMPLATE_URL + SLASH + randomTeacher.getId()).contentType(MediaType.APPLICATION_FORM_URLENCODED).param(ID, String.valueOf(randomTeacher.getId()))).andExpect(status().is3xxRedirection());
//
//        verify(teacherService, times(1)).delete(randomTeacher.getId());
//        verifyNoMoreInteractions(teacherService);
//    }
//}
