async function loadProfile() {
    console.log("Loading profile...");
    const token = localStorage.getItem("token");

    if (!token) {
        console.warn("No token found. Redirecting to login.");
        alert("로그인이 필요합니다.");
        window.location.href = "/view/login";
        return;
    }

    try {
        const response = await fetch("/auth/v1/profile", {
            method: "GET",
            headers: {
                "Authorization": "Bearer " + token,
                "Content-Type": "application/json"
            }
        });

        if (response.ok) {
            const result = await response.json();
            console.log("Profile data received:", result);

            if (result.success && result.data) {
                document.getElementById("userEmail").innerText = result.data.email;
                document.getElementById("userRoles").innerText = result.data.roles.map(roleObj => roleObj.roleName).join(", ");
            }
        } else {
            alert("세션이 만료되었습니다. 다시 로그인해주세요.");
            localStorage.removeItem("token");
            window.location.href = "/view/login";
        }
    } catch (error) {
        console.error("Profile API error:", error);
    }
}

function logout() {
    localStorage.removeItem("token");
    alert("로그아웃 되었습니다.");
    window.location.href = "/view/login";
}

// ✅ 페이지 로딩 시 실행
document.addEventListener("DOMContentLoaded", () => {
    console.log("DOMContentLoaded - Executing loadProfile()");
    loadProfile();
});