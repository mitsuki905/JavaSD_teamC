<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="<c:url value='/css/style.css'/>">
<c:import url="/base.jsp">
    <c:param name="body">

		<h2>　成績管理</h2><br>

	    <%-- 「登録して再度入力」が実行された際に表示 --%>
	    <%-- 「削除して再度入力」が実行された際に表示 --%>
	    <p>${ rechance }</p>


	    <%-- 検索フォーム --%>
	    <div class="bg-light p-3 rounded mb-4">
	        <form action="test_regist" method="post">
	            <div class="row g-3 align-items-end">

	                <%-- 入学年度 --%>
	                <div class="col-md-2">
	                    <label for="f_ent_year" class="form-label">入学年度</label>
	                    <select name="f_ent_year" id="f_ent_year" class="form-select">
	                        <option value="0">--------</option>
	                        <c:forEach var="year" items="${entYearSet}">
								<option value="${year}" <c:if test="${year == f_ent_year}">selected</c:if>>${year}</option>
							</c:forEach>
	                    </select>
	                </div>

	                <%-- クラス --%>
	                <div class="col-md-2">
	                    <label for="f_class_num" class="form-label">クラス</label>
	                    <select name="f_class_num" id="f_class_num" class="form-select">
	                        <option value="0">--------</option>
	                        <c:forEach var="classItem" items="${classList}">
	                            <option value="${classItem}" <c:if test="${classItem == f_class_num}">selected</c:if>>${classItem}</option>
	                        </c:forEach>
	                    </select>
	                </div>

	                <%-- 科目 --%>
	                <div class="col-md-3">
	                    <label for="f_subject_cd" class="form-label">科目</label>
	                    <select name="f_subject_cd" id="f_subject_cd" class="form-select">
	                        <option value="0">--------</option>
	                        <c:forEach var="subjectItem" items="${subjectList}">
	                            <option value="${subjectItem.cd}" <c:if test="${subjectItem.cd == f_subject_cd}">selected</c:if>>${subjectItem.name}</option>
	                        </c:forEach>
	                    </select>
	                </div>

	                <%-- 回数 --%>
	                <div class="col-md-2">
	                    <label for="f_num" class="form-label">回数</label>
	                    <select name="f_num" id="f_num" class="form-select">
	                        <option value="0">--------</option>
	                        <c:forEach var="i" begin="1" end="2">
	                            <option value="${i}" <c:if test="${i == f_num}">selected</c:if>>${i}</option>
	                        </c:forEach>
	                    </select>
	                </div>

	                <div class="col-md-2 d-flex justify-content-end">
	                    <button type="submit" class="btn btn-secondary">検索</button>
	                </div>
	            </div>
	        </form>

	        <%-- エラーメッセージ表示エリア --%>
	        <c:if test="${not empty error}">
	        <div class="text-warning small">${error}</div>
	        	</c:if>
	   		</div>

		    <%-- 検索後、該当者がいなかった場合にメッセージ表示 --%>
		    <c:if test="${not empty f_ent_year && f_ent_year != 0 && empty students}">
	            <p>該当する学生は見つかりませんでした</p>
	        </c:if>

	        <%-- 検索結果の表示 --%>
	        <c:if test="${not empty students}">
	    		<form action="test_regist_execute" method="post" id="grade-form">
	                <p>科目：${subject.name} (${num}回)</p>
	                <div class="table-responsive">
	                    <table class="table table-striped table-hover table-uniform">
						    <thead>
						        <tr>
						            <th>入学年度</th>
						            <th>クラス</th>
						            <th>学生番号</th>
						            <th>氏名</th>
						            <th>点数</th>
						            <th class="text-center delete" >削除</th>
						        </tr>
						    </thead>
						    <tbody>
						        <c:forEach var="student" items="${students}">
						            <tr>
							                <td>${student.entYear}</td>
							                <td>${student.classNum}</td>
							                <td>${student.no}</td>
							                <td>${student.name}</td>
							                <td>
						                    <%-- 初期化 --%>
											<c:set var="pointValue" value="" />

											<%-- pointMapが存在する場合（再登録・再削除後） --%>
											<c:if test="${not empty pointMap}">
											    <c:set var="pointValue" value="${pointMap[student.no]}" />
											</c:if>

											<%-- 上記がない場合は testList をループして検索（初回検索時） --%>
											<c:if test="${empty pointMap}">
											    <c:forEach var="test" items="${testList}">
											        <c:if test="${test.student.no == student.no}">
											            <c:set var="pointValue" value="${test.point}" />
											        </c:if>
											    </c:forEach>
											</c:if>

<!-- 											<form id="scoreForm">
 -->											  <input type="text"
											         name="point_${ student.no }"
											         class="form-control point-input"
											         value="${pointValue}">
											  <div class="text-warning small error-message"></div>
										<!-- 	</form> -->



						                    <c:if test="${not empty errors}">
						                        <%-- <div class="text-warning small">${errors}</div> --%>
							                    <li class="text-warning error-message-item">${ errors }</li>
						                    </c:if>
						                </td>
						            	<input type="hidden" name="student_no" value="${student.no}">
						                <td class="text-center">
						                    <input class="form-check-input delete-check" type="checkbox" name="delete_student_no" value="${student.no}">
							            </td>
							        </tr>
						    	</c:forEach>
							</tbody>
						</table>

	                    <%-- 登録処理に必要な共通情報をhiddenで送信 --%>
	                    <input type="hidden" name="subject_cd" value="${subject.cd}">
	                    <input type="hidden" name="num" value="${num}">
	                    <input type="hidden" name="ent_year" value="${f_ent_year}">
	                    <input type="hidden" name="class_num" value="${f_class_num}">

	                    <div class="mt-4" id="button-area">
						    <%-- 登録モード用のボタン (最初は表示) --%>
						    <button type="submit" id="register-finish-btn" name="submit_action" value="register_finish" class="btn btn-secondary">登録して終了</button>
						    <button type="submit" id="register-again-btn" name="submit_action" value="register_again" class="btn btn-primary">登録して再度入力</button>

						    <%-- 削除モード用のボタン (最初は非表示: d-none) --%>
						    <button type="submit" id="delete-finish-btn" name="submit_action" value="delete_finish" class="btn btn-danger d-none">削除して終了</button>
						    <button type="submit" id="delete-again-btn" name="submit_action" value="delete_again" class="btn btn-warning d-none">削除して再度検索</button>
						</div>
	                </div>
	        	</form>
	    	</c:if>



			<script>
				document.addEventListener('DOMContentLoaded', function () {
				    const gradeForm = document.getElementById('grade-form');
				    if (!gradeForm) return;

				    const registerFinishBtn = document.getElementById('register-finish-btn');
				    const registerAgainBtn = document.getElementById('register-again-btn');
				    const deleteFinishBtn = document.getElementById('delete-finish-btn');
				    const deleteAgainBtn = document.getElementById('delete-again-btn');
				    const checkboxes = document.querySelectorAll('.delete-check');
				    const pointInputs = document.querySelectorAll('.point-input');

				    function validatePoints() {
				        let isValid = true;

				        // すでにあるエラーを削除
				        document.querySelectorAll('.point-error-message').forEach(el => el.remove());

				        pointInputs.forEach(input => {
				            const value = input.value.trim();
				            const parent = input.parentElement;

				            if (value === '') return;
				            const num = Number(value);
				            let errorMessage = '';

				            // ^\d+$  文字列全体が数字だけで構成されている
				            if (!/^\d+$/.test(value)) {
				                errorMessage = '整数を入力してください';
				                isValid = false;
				            } else if (num < 0 || num > 100) {
				                errorMessage = '0～100の整数を入力してください';
				                isValid = false;
				            }

				            if (errorMessage) {
				                const errorDiv = document.createElement('div');
				                errorDiv.className = 'text-warning small point-error-message';
				                errorDiv.innerText = errorMessage;
				                parent.appendChild(errorDiv);
				            }
				        });

				        return isValid;
				    }

				    // 登録 or 再入力ボタンのクリック時バリデーション
				    [registerFinishBtn, registerAgainBtn].forEach(button => {
				        button.addEventListener('click', function (e) {
				            if (!validatePoints()) {
				                e.preventDefault(); // フォーム送信ストップ
				            }
				        });
				    });

				    function updateFormState() {
				        const checkedCount = document.querySelectorAll('.delete-check:checked').length;

				        if (checkedCount > 0) {
				            gradeForm.action = 'test_delete_execute';
				            registerFinishBtn.classList.add('d-none');
				            registerAgainBtn.classList.add('d-none');
				            deleteFinishBtn.classList.remove('d-none');
				            deleteAgainBtn.classList.remove('d-none');
				            pointInputs.forEach(input => input.disabled = true);
				        } else {
				            gradeForm.action = 'test_regist_execute';
				            registerFinishBtn.classList.remove('d-none');
				            registerAgainBtn.classList.remove('d-none');
				            deleteFinishBtn.classList.add('d-none');
				            deleteAgainBtn.classList.add('d-none');
				            pointInputs.forEach(input => input.disabled = false);
				        }
				    }

				    checkboxes.forEach(checkbox => {
				        checkbox.addEventListener('change', updateFormState);
				    });

				    updateFormState(); // 初期設定
				});
				</script>

	</c:param>
</c:import>