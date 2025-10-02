package com.portfolio.blog_api.service;

import com.portfolio.blog_api.dto.PostRequest;
import com.portfolio.blog_api.dto.PostResponse;
import com.portfolio.blog_api.exception.ResourceNotFoundException;
import com.portfolio.blog_api.model.Post;
import com.portfolio.blog_api.model.User;
import com.portfolio.blog_api.repository.PostRepository;
import com.portfolio.blog_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Busca todos os posts no banco de dados.
     * @return Uma lista de PostResponse DTOs.
     */
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll().stream()
                .map(this::convertToPostResponse)
                .collect(Collectors.toList());
    }

    /**
     * Busca um único post pelo seu ID.
     * @param id O ID do post a ser buscado.
     * @return O PostResponse DTO correspondente.
     * @throws ResourceNotFoundException se o post não for encontrado.
     */
    public PostResponse getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post não encontrado com o id: " + id));
        return convertToPostResponse(post);
    }

    /**
     * Cria um novo post.
     * @param postRequest DTO com os dados para o novo post.
     * @param authorDetails Detalhes do usuário autenticado (autor do post).
     * @return O PostResponse DTO do post recém-criado.
     */
    public PostResponse createPost(PostRequest postRequest, UserDetails authorDetails) {
        // Busca o usuário (autor) no banco de dados pelo username
        User author = userRepository.findByUsername(authorDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário autor não encontrado."));

        // Cria a nova entidade Post
        Post post = new Post();
        post.setTitle(postRequest.title());
        post.setContent(postRequest.content());
        post.setAuthor(author);
        post.setCreatedAt(LocalDateTime.now());

        // Salva o post no banco de dados
        Post savedPost = postRepository.save(post);

        // Converte a entidade salva para o DTO de resposta e a retorna
        return convertToPostResponse(savedPost);
    }

    /**
     * Atualiza um post existente.
     * @param id O ID do post a ser atualizado.
     * @param postRequest DTO com os novos dados do post.
     * @param currentUserDetails Detalhes do usuário que está tentando fazer a atualização.
     * @return O PostResponse DTO do post atualizado.
     * @throws ResourceNotFoundException se o post não for encontrado.
     * @throws AccessDeniedException se o usuário atual não for o autor do post.
     */
    public PostResponse updatePost(Long id, PostRequest postRequest, UserDetails currentUserDetails) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post não encontrado com o id: " + id));

        // VERIFICAÇÃO DE SEGURANÇA: Garante que o usuário logado é o autor do post.
        if (!post.getAuthor().getUsername().equals(currentUserDetails.getUsername())) {
            throw new AccessDeniedException("Acesso negado: Você não é o autor deste post.");
        }

        post.setTitle(postRequest.title());
        post.setContent(postRequest.content());

        Post updatedPost = postRepository.save(post);
        return convertToPostResponse(updatedPost);
    }

    /**
     * Deleta um post.
     * @param id O ID do post a ser deletado.
     * @param currentUserDetails Detalhes do usuário que está tentando deletar.
     * @throws ResourceNotFoundException se o post não for encontrado.
     * @throws AccessDeniedException se o usuário atual não for o autor do post ou um ADMIN.
     */
    public void deletePost(Long id, UserDetails currentUserDetails) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post não encontrado com o id: " + id));

        // VERIFICAÇÃO DE SEGURANÇA: Permite a exclusão se o usuário for o autor OU se tiver o papel de ADMIN.
        boolean isAdmin = currentUserDetails.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ADMIN"));
        boolean isAuthor = post.getAuthor().getUsername().equals(currentUserDetails.getUsername());

        if (!isAuthor && !isAdmin) {
            throw new AccessDeniedException("Acesso negado: Você não tem permissão para deletar este post.");
        }

        postRepository.delete(post);
    }

    /**
     * Método utilitário privado para converter uma entidade Post para um PostResponse DTO.
     * @param post A entidade Post a ser convertida.
     * @return O PostResponse DTO.
     */
    private PostResponse convertToPostResponse(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCreatedAt(),
                post.getAuthor().getUsername()
        );
    }
}