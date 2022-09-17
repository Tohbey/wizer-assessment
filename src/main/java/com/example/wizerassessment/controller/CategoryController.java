package com.example.wizerassessment.controller;

import com.example.wizerassessment.dtos.ResponseObject;
import com.example.wizerassessment.mapper.DTOS.CategoryDTO;
import com.example.wizerassessment.mapper.DTOS.CategoryListDTO;
import com.example.wizerassessment.model.Category;
import com.example.wizerassessment.services.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(CategoryController.BASE_URL)
public class CategoryController {
    public static final String BASE_URL = "/api/v1/category";

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ResponseObject<CategoryDTO>> saveCategory(@RequestBody Category category){
        ResponseObject<CategoryDTO> object = new ResponseObject<>();
        try {
            CategoryDTO categoryDTO = this.categoryService.createCategory(category);
            object.setValid(true);
            object.setData(Collections.singletonList(categoryDTO));
            object.setMessage("Resource Created Successfully");
        }catch (Exception e){
            object.setValid(false);
            object.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return ResponseEntity.ok().body(object);
    }

    @RequestMapping(value = "/{categoryId}",method = RequestMethod.PUT)
    public ResponseEntity<ResponseObject<CategoryDTO>> updateCategory(@RequestBody Category category, @PathVariable("categoryId") Long categoryId){
        ResponseObject<CategoryDTO> object = new ResponseObject<>();
        try {
            CategoryDTO categoryDTO = this.categoryService.editCategory(category, categoryId).get();
            object.setValid(true);
            object.setData(Collections.singletonList(categoryDTO));
            object.setMessage("Resource Updated Successfully");
        }catch (Exception e){
            object.setValid(false);
            object.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return ResponseEntity.ok().body(object);
    }

    @RequestMapping(value = "/{categoryId}",method = RequestMethod.DELETE)
    public ResponseEntity<ResponseObject> deleteCategory(@PathVariable("categoryId") Long categoryId){
        ResponseObject object = new ResponseObject<>();
        try {
            this.categoryService.deleteCategory(categoryId);
            object.setValid(true);
            object.setMessage("Resource Deleted Successfully");
        }catch (Exception e){
            object.setValid(false);
            object.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return ResponseEntity.ok().body(object);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<ResponseObject<CategoryListDTO>> getCategories(){
        ResponseObject<CategoryListDTO> object = new ResponseObject<>();
        try {
            List<CategoryDTO> categories = this.categoryService.getCategories();
            object.setValid(true);
            object.setData(Collections.singletonList(new CategoryListDTO(categories)));
            object.setMessage("Resource Retrieved Successfully");
        }catch (Exception e){
            object.setValid(false);
            object.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return ResponseEntity.ok().body(object);
    }
}
