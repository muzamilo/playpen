<?PHP

require($include.'head.inc');

echo "
	<body id='login' class='animated fadeInDown'>
		<!-- possible classes: minified, no-right-panel, fixed-ribbon, fixed-header, fixed-width-->
		<header id='header'>
			<!--<span id='logo'></span>-->

			<div id='logo-group'>
				<span id='logo'> <img src='img/logo.png' alt='SmartAdmin'> </span>

				<!-- END AJAX-DROPDOWN -->
			</div>

			<span id='login-header-space'> <span class='hidden-mobile'>Need an account?</span> <a href='register.php' class='btn btn-danger'>Sign Up</a> </span>

		</header>

		<div id='main' role='main'>

			<!-- MAIN CONTENT -->
			<div id='content' class='container'>

				<div class='row'>
					<div class='col-xs-12 col-sm-12 col-md-7 col-lg-8 hidden-xs hidden-sm'>
						<h1 class='txt-color-red login-header-big'>BASSAT</h1>
						<div class='hero'>

							<div class='pull-left login-desc-box-l'>
								<h4 class='paragraph-header'>Bank Account Statement Statistical Aggregation Tool</h4>
								<div class='login-app-icons'>
									<a href='javascript:void(0);' class='btn btn-danger btn-sm'>Find out more</a>
								</div>
							</div>

							<img src='img/login.jpg' class='pull-left display-image' alt='' style='width:300px'>

						</div>

						<div class='row'>
							<div class='col-xs-12 col-sm-12 col-md-6 col-lg-6'>
								<h5 class='about-heading'>About Bassat - Are you up to date?</h5>
								<p>
									Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa.
								</p>
							</div>
							<div class='col-xs-12 col-sm-12 col-md-6 col-lg-6'>
								<h5 class='about-heading'>Not just your average template!</h5>
								<p>
									Et harum quidem rerum facilis est et expedita distinctio. Nam libero tempore, cum soluta nobis est eligendi voluptatem accusantium!
								</p>
							</div>
						</div>

					</div>

					<div class='col-xs-12 col-sm-12 col-md-5 col-lg-4'>

					";
                        if (isset($error_message) && $error_message != '') {
                            echo "
                            <div class='alert alert-danger fade in'>
                                <button class='close' data-dismiss='alert'>
                                    ×
                                </button>
                                <i class='fa-fw fa fa-warning'></i>
                                $error_message
                            </div>";
                        }
                    echo "

						<div class='well no-padding'>
							<form action='login.php' id='login-form' method='post' class='smart-form client-form'>
								<header>
									Sign In
								</header>

								 <fieldset>

									<section>
										<label class='label'>E-mail</label>
										<label class='input'> <i class='icon-append fa fa-user'></i>
											<input type='email' name='email' value='$email'>
											<b class='tooltip tooltip-top-right'><i class='fa fa-user txt-color-teal'></i> Please enter email address/username</b></label>
									</section>

									<section>
										<label class='label'>Password</label>
										<label class='input'> <i class='icon-append fa fa-lock'></i>
											<input type='password' name='password' value = ''>
											<b class='tooltip tooltip-top-right'><i class='fa fa-lock txt-color-teal'></i> Enter your password</b> </label>
										<div class='note'>
											<a href='forgotpassword.html'>Forgot password?</a>
										</div>
									</section>

									<section>
										<label class='checkbox'>
											<input type='checkbox' name='remember' checked=''>
											<i></i>Stay signed in</label>
									</section>
								</fieldset>

								<footer>
									<button type='submit' class='btn btn-primary' name='login'>
										Sign in
									</button>
								</footer>
							</form>

						</div>

					</div>
				</div>
			</div>

		</div>

		<!--================================================== -->";

        include $include.'full_js_dependencies.html';

        echo "

		<script type='text/javascript'>
			runAllForms();

			$(function() {
				// Validation
				$('#login-form').validate({
					// Rules for form validation
					rules : {
						email : {
							required : true,
							email : true
						},
						password : {
							required : true,
							minlength : 3,
							maxlength : 20
						}
					},

					// Messages for form validation
					messages : {
						email : {
							required : 'Please enter your email address',
							email : 'Please enter a VALID email address'
						},
						password : {
							required : 'Please enter your password'
						}
					},

					// Do not change code below
					errorPlacement : function(error, element) {
						error.insertAfter(element.parent());
					}
				});
			});
		</script>

	</body>
";

require($include.'footer.inc');

?>