package gift.service;

import gift.domain.Category;
import gift.domain.Product;
import gift.exception.ProductNotFoundException;
import gift.repository.CategoryRepository;
import gift.repository.ProductRepository;
import gift.request.OptionRequest;
import gift.request.ProductCreateRequest;
import gift.request.ProductUpdateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductService productService;

    @DisplayName("모든 상품 정보를 조회해 반환한다.")
    @Test
    void getProducts() throws Exception {
        //given
        PageRequest pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "id"));
        given(productRepository.findAll(pageable)).willReturn(new PageImpl<>(List.of()));

        //when
        productService.getProducts(pageable);

        //then
        then(productRepository).should().findAll(pageable);
    }

    @DisplayName("상품 ID를 받아 해당하는 상품 정보를 조회한다.")
    @Test
    void getProduct() throws Exception {
        //given
        Long productId = 1L;

        given(productRepository.findById(productId)).willReturn(Optional.of(new Product()));

        //when
        productService.getProduct(productId);

        //then
        then(productRepository).should().findById(productId);
    }

    @DisplayName("상품 ID를 받아 해당하는 상품 정보를 조회하는데, 존재하지 않는 상품이면 예외를 던진다.")
    @Test
    void getProductWithNonExistingProduct() throws Exception {
        //given
        Long productId = 1L;

        given(productRepository.findById(productId)).willReturn(Optional.empty());

        //when & then
        assertThrows(ProductNotFoundException.class, () -> productService.getProduct(productId));

        then(productRepository).should().findById(productId);
    }

    @DisplayName("상품 하나를 추가한다.")
    @Test
    void addProduct() throws Exception {
        //given
        Long categoryId = 1L;
        ProductCreateRequest request = new ProductCreateRequest("아이스티", 2500, "https://example.com", categoryId, List.of(new OptionRequest("옵션", 123L)));

        given(productRepository.save(any(Product.class))).willReturn(new Product());
        given(categoryRepository.findById(anyLong())).willReturn(Optional.of(new Category()));

        //when
        productService.addProduct(request);

        //then
        then(productRepository).should().save(any(Product.class));
        then(categoryRepository).should().findById(anyLong());
    }

    @DisplayName("상품 정보를 수정한다.")
    @Test
    void editProduct() throws Exception {
        //given
        Long productId = 1L;
        Long categoryId = 1L;
        ProductUpdateRequest request = new ProductUpdateRequest("아이스티", 2500, "https://example.com", categoryId);

        given(productRepository.findById(productId)).willReturn(Optional.of(new Product()));
        given(categoryRepository.findById(categoryId)).willReturn(Optional.of(new Category()));

        //when
        productService.editProduct(productId, request);

        //then
        then(productRepository).should().findById(productId);
        then(categoryRepository).should().findById(categoryId);
    }

    @DisplayName("상품 ID를 받아 해당하는 상품을 삭제한다.")
    @Test
    void removeProduct() throws Exception {
        //given
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);

        given(productRepository.findById(productId)).willReturn(Optional.of(product));
        willDoNothing().given(productRepository).deleteById(productId);

        //when
        productService.removeProduct(productId);

        //then
        then(productRepository).should().deleteById(productId);
    }

    @DisplayName("상품 ID를 받아 해당하는 상품을 삭제하는데, 존재하지 않는 상품이면 예외를 던진다.")
    @Test
    void removeProductWithNonExistingProduct() throws Exception {
        //given
        Long productId = 1L;

        given(productRepository.findById(productId)).willReturn(Optional.empty());

        //when & then
        assertThrows(ProductNotFoundException.class, () -> productService.removeProduct(productId));

        then(productRepository).should().findById(productId);
    }

}
