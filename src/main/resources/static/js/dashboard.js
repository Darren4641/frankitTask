let hasAdminRole = false;
let currentProduct = null;

// 로그아웃 함수
function logout() {
    localStorage.removeItem("token");
    window.location.href = "/view/login";
}

// 프로필 정보 로드
async function loadProfile() {
    const token = localStorage.getItem("token");
    if (!token) {
        alert("로그인이 필요합니다.");
        window.location.href = "/view/login";
        return;
    }
    try {
        const response = await fetch("/user/v1/profile", {
            method: "GET",
            headers: {
                "Authorization": "Bearer " + token,
                "Content-Type": "application/json"
            }
        });
        if (response.ok) {
            const result = await response.json();
            document.getElementById("userEmail").innerText = result.data.email;
            // roles 예: [{ role: "ROLE_ADMIN", roleName: "관리자" }, { role: "ROLE_USER", roleName: "일반" }]
            const roles = result.data.roles.map(r => r.role);
            if (roles.includes("ROLE_ADMIN")) {
                hasAdminRole = true;
            }
            const roleNames = result.data.roles.map(r => r.roleName);
            document.getElementById("userRoles").innerText = roleNames.join(", ");
        } else {
            alert("세션이 만료되었습니다. 다시 로그인해주세요.");
            logout();
        }
    } catch (error) {
        console.error("Profile API error:", error);
    }
}

// 상품 목록 로드 (페이징 처리)
async function loadProducts(page = 0, size = 10) {
    const token = localStorage.getItem("token");
    try {
        const response = await fetch(`/product/v1?page=${page}&size=${size}`, {
            method: "GET",
            headers: {
                "Authorization": "Bearer " + token,
                "Content-Type": "application/json"
            }
        });
        if (response.ok) {
            const result = await response.json();
            displayProducts(result.data.content);
            setupPagination(result.data);
        } else {
            console.error("Failed to load products.");
        }
    } catch (error) {
        console.error("Product list API error:", error);
    }
}

// 상품 목록 렌더링
function displayProducts(products) {
    const productListDiv = document.getElementById("productListContainer");
    productListDiv.innerHTML = "";
    if (!products || products.length === 0) {
        productListDiv.innerHTML = "<p>등록된 상품이 없습니다.</p>";
        return;
    }
    products.forEach(product => {
        const itemDiv = document.createElement("div");
        itemDiv.classList.add("border", "p-2", "mb-2", "product-item");
        itemDiv.style.cursor = "pointer";
        itemDiv.innerHTML = `
            <h5 style="text-align: left;">${product.name} (ID: ${product.id})</h5>
            <p>${product.description}</p>
            <p>가격: ${product.price} | 배송비: ${product.deliveryFee}</p>
            <p>등록일: ${product.createdDate}</p>
            <p>판매자: ${product.sellerEmail}</p>
        `;
        itemDiv.addEventListener("click", () => loadProductDetail(product.id));
        productListDiv.appendChild(itemDiv);
    });
}

// 페이징 구성
function setupPagination(pageData) {
    const paginationDiv = document.getElementById("productPagination");
    paginationDiv.innerHTML = "";
    const { number: currentPage, totalPages, size } = pageData;
    if (currentPage > 0) {
        const prevBtn = document.createElement("button");
        prevBtn.classList.add("btn", "btn-secondary", "me-2");
        prevBtn.textContent = "이전";
        prevBtn.addEventListener("click", () => loadProducts(currentPage - 1, size));
        paginationDiv.appendChild(prevBtn);
    }
    if (currentPage < totalPages - 1) {
        const nextBtn = document.createElement("button");
        nextBtn.classList.add("btn", "btn-secondary");
        nextBtn.textContent = "다음";
        nextBtn.addEventListener("click", () => loadProducts(currentPage + 1, size));
        paginationDiv.appendChild(nextBtn);
    }
}

// 상품 상세 로드
async function loadProductDetail(productId) {
    const token = localStorage.getItem("token");
    try {
        const response = await fetch(`/product/v1/${productId}`, {
            method: "GET",
            headers: {
                "Authorization": "Bearer " + token,
                "Content-Type": "application/json"
            }
        });
        if (response.ok) {
            const result = await response.json();
            currentProduct = result.data; // 저장
            displayProductDetail(currentProduct);
        } else {
            console.error("상품 상세 로드 실패");
        }
    } catch (error) {
        console.error("상품 상세 API error:", error);
    }
}

// 상품 상세 렌더링 및 관리자 버튼 추가
function displayProductDetail(product) {
    const detailContainer = document.getElementById("productDetailContainer");
    detailContainer.innerHTML = `
        <h5>${product.name} (ID: ${product.id})</h5>
        <p>${product.description}</p>
        <p>가격: ${product.price} | 배송비: ${product.deliveryFee}</p>
        <p>등록일: ${product.createdDate}</p>
        <p>판매자: ${product.sellerEmail}</p>
        <hr>
        <h6>옵션 목록</h6>
    `;
    // 옵션 SELECT (모든 옵션 표시)
    const selectEl = document.createElement("select");
    selectEl.classList.add("form-select", "mt-2");
    if (!product.options || product.options.length === 0) {
        const emptyOpt = document.createElement("option");
        emptyOpt.textContent = "옵션 없음";
        selectEl.appendChild(emptyOpt);
    } else {
        product.options.forEach(opt => {
            const optionTag = document.createElement("option");
            optionTag.value = opt.id;
            optionTag.textContent = `${opt.optionName} [${opt.optionType}] (+${opt.extraPrice})`;
            selectEl.appendChild(optionTag);
        });
    }
    detailContainer.appendChild(selectEl);

    // 관리자 전용 기능
    if (hasAdminRole) {
        const adminDiv = document.createElement("div");
        adminDiv.classList.add("mt-3");
        adminDiv.innerHTML = `
            <h6>관리자 전용 기능</h6>
            <button class="btn btn-warning me-2" onclick="openUpdateProductModal(${product.id})" style="width: 100%; margin-bottom: 5px;">상품 수정</button>
            <button class="btn btn-danger me-2" onclick="deleteProduct(${product.id})" style="width: 100%; margin-bottom: 5px;">상품 삭제</button>
            <button class="btn btn-secondary me-2" onclick="openInputOptionModal(${product.id})" style="width: 100%; margin-bottom: 5px;">Input 옵션 추가</button>
            <button class="btn btn-secondary" onclick="openSelectOptionModal(${product.id})" style="width: 100%; margin-bottom: 5px;">Select 옵션 추가</button>
            <button class="btn btn-danger" onclick="deleteProductOption()" style="width: 100%; margin-bottom: 5px;">옵션 삭제</button>
        `;
        detailContainer.appendChild(adminDiv);
    }
}

// --- 모달 제어 함수 ---
function showModal(modalId) {
    const modalElement = document.getElementById(modalId);
    const modal = new bootstrap.Modal(modalElement);
    modal.show();
}
function closeModal(modalId) {
    const modalElement = document.getElementById(modalId);
    const modalInstance = bootstrap.Modal.getInstance(modalElement);
    if (modalInstance) {
        modalInstance.hide();
    } else {
        // 인스턴스가 없으면 새로 생성 후 hide() 호출
        new bootstrap.Modal(modalElement).hide();
    }
}
function openUpdateProductModal(productId) {
    // 채워 넣기
    if (currentProduct && currentProduct.id === productId) {
        document.getElementById("updateProductId").value = currentProduct.id;
        document.getElementById("updateProductName").value = currentProduct.name;
        document.getElementById("updateProductDescription").value = currentProduct.description;
        document.getElementById("updateProductPrice").value = currentProduct.price;
        document.getElementById("updateProductDeliveryFee").value = currentProduct.deliveryFee;
        showModal("productUpdateModal");
    }
}

function openInputOptionModal(productId) {
    document.getElementById("inputOptionProductIdModal").value = productId;
    showModal("inputOptionModal");
}

function openSelectOptionModal(productId) {
    document.getElementById("selectOptionProductIdModal").value = productId;
    showModal("selectOptionModal");
}

// --- 모달 폼 제출 처리 ---
// 상품 수정
document.getElementById("productUpdateForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    const token = localStorage.getItem("token");
    const productId = document.getElementById("updateProductId").value;
    const dto = {
        productId: productId,
        name: document.getElementById("updateProductName").value,
        description: document.getElementById("updateProductDescription").value,
        price: parseFloat(document.getElementById("updateProductPrice").value),
        deliveryFee: parseFloat(document.getElementById("updateProductDeliveryFee").value)
    };
    try {
        const response = await fetch(`/product/v1`, {
            method: "PUT",
            headers: {
                "Authorization": "Bearer " + token,
                "Content-Type": "application/json"
            },
            body: JSON.stringify(dto)
        });
        if (response.ok) {
            alert("상품 수정 성공!");
            loadProducts();
            loadProductDetail(productId);
            closeModal("productUpdateModal");
        } else {
            const errorData = await response.json();
            alert(errorData.message);
        }
    } catch (error) {
        console.error("상품 수정 API error:", error);
    }
});

document.getElementById("productRegistrationForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    const token = localStorage.getItem("token");
    const deliveryFee = parseInt(document.getElementById("productDeliveryFee").value);
    const name = document.getElementById("productName").value;
    const description = document.getElementById("productDescription").value;
    const price = parseFloat(document.getElementById("productPrice").value);

    try {
        const response = await fetch("/product/v1/registration", {
            method: "POST",
            headers: {
                "Authorization": "Bearer " + token,
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ name, description, price, deliveryFee })
        });
        if (response.ok) {
            alert("상품 등록 성공!");
            loadProducts();
            document.getElementById("productDeliveryFee").value = "";
            document.getElementById("productName").value = "";
            document.getElementById("productDescription").value = "";
            document.getElementById("productPrice").value = "";
        } else {
            const errorData = await response.json();
            alert(errorData.message);
        }
    } catch (error) {
        console.error("Input 옵션 등록 API error:", error);
    }
});

// Input 옵션 추가
document.getElementById("inputOptionModalForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    const token = localStorage.getItem("token");
    const productId = parseInt(document.getElementById("inputOptionProductIdModal").value);
    const optionName = document.getElementById("inputOptionNameModal").value;
    const extraPrice = parseFloat(document.getElementById("inputOptionExtraPriceModal").value);
    try {
        const response = await fetch("/product/v1/registration/input-option", {
            method: "POST",
            headers: {
                "Authorization": "Bearer " + token,
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ productId, optionName, extraPrice })
        });
        if (response.ok) {
            alert("Input 옵션 등록 성공!");
            loadProductDetail(productId);
            closeModal("inputOptionModal");
        } else {
            const errorData = await response.json();
            alert(errorData.message);
        }
    } catch (error) {
        console.error("Input 옵션 등록 API error:", error);
    }
});

// Select 옵션 추가
document.getElementById("selectOptionModalForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    const token = localStorage.getItem("token");
    const productId = parseInt(document.getElementById("selectOptionProductIdModal").value);
    const optionId = parseInt(document.getElementById("selectOptionIdModal").value);
    try {
        const response = await fetch("/product/v1/registration/select-option", {
            method: "POST",
            headers: {
                "Authorization": "Bearer " + token,
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ productId, optionId })
        });
        if (response.ok) {
            alert("Select 옵션 등록 성공!");
            loadProductDetail(productId);
            closeModal("selectOptionModal");
        } else {
            const errorData = await response.json();
            alert(errorData.message);
        }
    } catch (error) {
        console.error("Select 옵션 등록 API error:", error);
    }
});

// 공통 옵션 로드 및 select 채우기
async function loadCommonOptions() {
    const token = localStorage.getItem("token");
    try {
        const response = await fetch("/product/v1/common/option", {
            method: "GET",
            headers: {
                "Authorization": "Bearer " + token,
                "Content-Type": "application/json"
            }
        });
        if (response.ok) {
            const result = await response.json();
            const selectEl = document.getElementById("selectOptionIdModal");
            selectEl.innerHTML = "";
            result.data.forEach(opt => {
                selectEl.insertAdjacentHTML("beforeend", `<option value="${opt.id}">${opt.optionName}</option>`);
            });
            displayCommonOptions(result.data);
        } else {
            const errorData = await response.json();
            alert(errorData.message);
        }
    } catch (error) {
        console.error("공통 옵션 API error:", error);
    }
}

function displayCommonOptions(options) {
    const commonOptionListDiv = document.getElementById("commonOptionList");
    commonOptionListDiv.innerHTML = "";
    if (!options || options.length === 0) {
        commonOptionListDiv.innerHTML = "<p>등록된 공통 옵션이 없습니다.</p>";
        return;
    }
    options.forEach(option => {
        const optHtml = `
            <div class="border p-2 mb-2">
                <p><strong>ID:</strong> ${option.id}</p>
                <p><strong>옵션명:</strong> ${option.optionName}</p>
            </div>
        `;
        commonOptionListDiv.insertAdjacentHTML("beforeend", optHtml);
    });
}

// 상품 삭제 함수 (DELETE /product/v1)
async function deleteProduct(productId) {
    if (!confirm("정말 상품을 삭제하시겠습니까?")) return;

    const token = localStorage.getItem("token");
    try {
        // DELETE API는 request body에 productId를 포함 (controller의 DELETE 매핑에 맞춰서)
        const response = await fetch(`/product/v1`, {
            method: "DELETE",
            headers: {
                "Authorization": "Bearer " + token,
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ productId: productId })
        });
        if (response.ok) {
            alert("상품 삭제 성공!");
            loadProducts(); // 목록 새로고침
            document.getElementById("productDetailContainer").innerHTML = "<p>상품이 삭제되었습니다.</p>";
        } else {
            const errorData = await response.json();
            alert(errorData.message || "상품 삭제 실패!");
        }
    } catch (error) {
        console.error("상품 삭제 API error:", error);
    }
}

// 상품 옵션 삭제 함수 (DELETE /product/v1/option)
async function deleteProductOption() {
    const token = localStorage.getItem("token");
    // 상품 상세 영역 내 select 요소에서 선택된 옵션을 삭제 대상으로 함
    const selectEl = document.querySelector("#productDetailContainer select");
    if (!selectEl) {
        alert("삭제할 옵션을 선택할 수 없습니다.");
        return;
    }
    const optionId = selectEl.value;
    if (!optionId || optionId === '옵션 없음') {
        alert("옵션이 선택되지 않았습니다.");
        return;
    }
    if (!confirm(`현재 옵션을 삭제하시겠습니까?`)) return;

    try {
        const response = await fetch(`/product/v1/option`, {
            method: "DELETE",
            headers: {
                "Authorization": "Bearer " + token,
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ optionId: optionId })
        });
        if (response.ok) {
            alert("옵션 삭제 성공!");
            // 상세 정보 재로딩 (상품 상세의 옵션 목록 갱신)
            loadProductDetail(currentProduct.id);
        } else {
            const errorData = await response.json();
            alert(errorData.message || "옵션 삭제 실패!");
        }
    } catch (error) {
        console.error("옵션 삭제 API error:", error);
    }
}

document.addEventListener("DOMContentLoaded", () => {
    loadProfile();
    loadProducts();
    loadCommonOptions();
});