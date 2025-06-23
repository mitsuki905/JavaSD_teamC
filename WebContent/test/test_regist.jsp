<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="<c:url value='/css/style.css'/>">
<c:import url="/base.jsp">
    <c:param name="body">

		<h2>　成績管理</h2><br>

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
						                    <input type="text" name="point_${student.no}" class="form-control test-r point-input" value="${points[student.no]}">
						                    <c:if test="${not empty errors[student.no]}">
						                        <div class="text-warning small">${errors[student.no]}</div>
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
				document.addEventListener('DOMContentLoaded', function() {
		        // フォームが存在しない場合は処理を終了
		        const gradeForm = document.getElementById('grade-form');
		        if (!gradeForm) {
		            return;
		        }

		        // 必要なDOM要素を取得
		        const registerFinishBtn = document.getElementById('register-finish-btn');
		        const registerAgainBtn = document.getElementById('register-again-btn');
		        const deleteFinishBtn = document.getElementById('delete-finish-btn');
		        const deleteAgainBtn = document.getElementById('delete-again-btn');
		        const checkboxes = document.querySelectorAll('.delete-check');
		        const pointInputs = document.querySelectorAll('.point-input');

		        // フォームの状態を更新する関数
		        function updateFormState() {
		            // チェックされたチェックボックスの数を取得
		            const checkedCount = document.querySelectorAll('.delete-check:checked').length;

		            if (checkedCount > 0) {
		                // --- 削除モード (チェックボックスが1つ以上選択されている) ---

		                // フォームの送信先を削除用アクションに設定
		                gradeForm.action = 'test_delete_execute';

		                // 登録ボタンを隠し、削除ボタンを表示
		               registerFinishBtn.classList.add('d-none');
		                registerAgainBtn.classList.add('d-none');
		                deleteFinishBtn.classList.remove('d-none');
		                deleteAgainBtn.classList.remove('d-none');

		                // 点数入力欄を無効化
		                pointInputs.forEach(input => {
		                    input.disabled = true;
		                });

		            } else {
		                // --- 登録モード (チェックボックスが選択されていない) ---

		                // フォームの送信先を登録用アクションに設定
		                gradeForm.action = 'test_regist_execute';

		                // 削除ボタンを隠し、登録ボタンを表示
		                registerFinishBtn.classList.remove('d-none');
		                registerAgainBtn.classList.remove('d-none');
		                deleteFinishBtn.classList.add('d-none');
		                deleteAgainBtn.classList.add('d-none');

		                // 点数入力欄を有効化
		                pointInputs.forEach(input => {
		                    input.disabled = false;
		                });
		            }
		        }

		        // 各チェックボックスの変更を監視し、変更があればフォームの状態を更新
		        checkboxes.forEach(checkbox => {
		            checkbox.addEventListener('change', updateFormState);
		        });

		        // ページ読み込み時に一度実行して初期状態を設定
		        updateFormState();
		    });
		</script>
	</c:param>
</c:import>