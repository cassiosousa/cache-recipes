package com.example.cacherecipes.openapi

import com.example.cacherecipes.persistence.entities.Recipes
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.MediaType
import org.springframework.http.ProblemDetail

@Operation(
    summary = "Cache Recipes Services Api",
    description = "Recipes apis",
    parameters = [
        Parameter(
            description = "Id of recipe",
            example = "2",
            name = "id",
            required = true,
            `in` = ParameterIn.PATH,
            schema = Schema(
                type = "integer"
            )
        )
    ],
    responses = [ApiResponse(
        responseCode = "200",
        description = "Successfully processed get recipes request",
        content = [

            Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = Schema(implementation = Recipes::class),
                        examples = [
                            ExampleObject(value = """
                                {
                                      "id": 2,
                                      "name": "Vegetarian Stir-Fry",
                                      "ingredients": [
                                        "Tofu, cubed",
                                        "Broccoli florets",
                                        "Carrots, sliced",
                                        "Bell peppers, sliced",
                                        "Soy sauce",
                                        "Ginger, minced",
                                        "Garlic, minced",
                                        "Sesame oil",
                                        "Cooked rice for serving"
                                      ],
                                      "instructions": [
                                        "In a wok, heat sesame oil over medium-high heat.",
                                        "Add minced ginger and garlic, sauté until fragrant.",
                                        "Add cubed tofu and stir-fry until golden brown.",
                                        "Add broccoli, carrots, and bell peppers. Cook until vegetables are tender-crisp.",
                                        "Pour soy sauce over the stir-fry and toss to combine.",
                                        "Serve over cooked rice."
                                      ],
                                      "prepTimeMinutes": 15,
                                      "cookTimeMinutes": 20,
                                      "servings": 3,
                                      "difficulty": "Medium",
                                      "cuisine": "Asian",
                                      "caloriesPerServing": 250,
                                      "tags": [
                                        "Vegetarian",
                                        "Stir-fry",
                                        "Asian"
                                      ],
                                      "userId": 58,
                                      "image": "https://cdn.dummyjson.com/recipe-images/2.webp",
                                      "rating": 4.7,
                                      "reviewCount": 36,
                                      "mealType": [
                                        "Lunch"
                                      ]
                                    }
                            """)
                        ]
            )

        ]
    ),
        ApiResponse(
            responseCode = "400",
            description = "Failed to process the request due to incorrect / missing parameter.",
            content = [
                (
                        Content(
                            mediaType = MediaType.TEXT_PLAIN_VALUE,
                            schema = Schema(implementation = ProblemDetail::class),
                            examples = [
                                ExampleObject(
                                    value = """{
                                                  "type": "about:blank",
                                                  "title": "Bad Request",
                                                  "status": 400,
                                                  "detail": "fail searching recipe with id:2",
                                                  "instance": "/v1/recipes/2"
                                                }
                                            """
                                )
                            ]
                        )
                        )
            ]
        ),
        ApiResponse(
            responseCode = "500",
            description = "Failed to process the request due to internal server error.",
            content = [
                (
                        Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = Schema(implementation = Any::class),
                        )
                        )
            ]
        )]
)
annotation class GetRecipesDoc()
