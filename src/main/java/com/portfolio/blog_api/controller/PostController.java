package com.portfolio.blog_api.controller;

import com.portfolio.blog_api.dto.PostRequest;
import com.portfolio.blog_api.dto.PostResponse;
import com.portfolio.blog_api.service.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    /**
     * Endpoint para criar um novo post.
     * Apenas usuários autenticados podem criar posts.
     * @param postRequest O corpo da requisição com os dados do post.
     * @param userDetails O usuário autenticado, injetado pelo Spring Security.
     * @return O post criado com status 201 Created.
     */
    @PostMapping
    public ResponseEntity<PostResponse> createPost(@Valid @RequestBody PostRequest postRequest,
                                                   @AuthenticationPrincipal UserDetails userDetails) {
        PostResponse createdPost = postService.createPost(postRequest, userDetails);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    /**
     * Endpoint para buscar todos os posts.
     * Geralmente, este é um endpoint público.
     * @return Uma lista de todos os posts com status 200 OK.
     */
    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        List<PostResponse> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    /**
     * Endpoint para buscar um post específico pelo seu ID.
     * @param id O ID do post.
     * @return O post encontrado com status 200 OK.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable Long id) {
        PostResponse post = postService.getPostById(id);
        return ResponseEntity.ok(post);
    }

    /**
     * Endpoint para atualizar um post existente.
     * Apenas o autor do post pode atualizá-lo.
     * @param id O ID do post a ser atualizado.
     * @param postRequest O corpo da requisição com os novos dados.
     * @param userDetails O usuário autenticado que está fazendo a requisição.
     * @return O post atualizado com status 200 OK.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> updatePost(@PathVariable Long id,
                                                   @Valid @RequestBody PostRequest postRequest,
                                                   @AuthenticationPrincipal UserDetails userDetails) {
        PostResponse updatedPost = postService.updatePost(id, postRequest, userDetails);
        return ResponseEntity.ok(updatedPost);
    }

    /**
     * Endpoint para deletar um post.
     * Apenas o autor do post ou um administrador pode deletá-lo.
     * @param id O ID do post a ser deletado.
     * @param userDetails O usuário autenticado.
     * @return Uma resposta vazia com status 204 No Content.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id,
                                           @AuthenticationPrincipal UserDetails userDetails) {
        postService.deletePost(id, userDetails);
        return ResponseEntity.noContent().build();
    }
}