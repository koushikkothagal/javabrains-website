(function () {
  'use strict';

  angular
  .module('javabrains', [])
  	.controller("QuizController", function (QuizData) {
        var vm = this;  
        vm.quizContent = QuizData.data;  
    })
    .directive('quiz', QuizDirective)
    .directive('translateX', TranslateDirective)
    .directive('codeQuiz', function ($compile) {
      return {
        'restrict': 'A',
        'scope': {
          'code': '=',
          'onChange': '&'
        },
        'link': function ($scope, element, attrs) {
          $scope.$watch(attrs, function () {

            if ($scope.code) {
              $scope.code = $scope.code.replace(/\n/g, '<br/>');

              var content = $.parseHTML($scope.code)[0];
              var codeContent = content.children[0]; // TODO: Look for code element here - like content.find('code')[0];
              hljs.configure({ 'useBR': true });
              hljs.highlightBlock(codeContent);
              var linkFn = $compile(content.outerHTML);
              content = linkFn($scope);
              element.append(content);
            }

          });
        }
      };
    })
    .factory('codeService', codeService)
    .filter('trust_html', ['$sce', function($sce){
        return function(text) {
            return $sce.trustAsHtml(text);
        };
    }]);

  /** @ngInject */
  function QuizDirective() {

    return {
      templateUrl: '/assets/scripts/quiz.html',
      scope: {
        'content': '=',
        'courseCode': '=',
        'nextPermalinkName': '='
      },
      controller: QuizModuleCtrl,
      controllerAs: 'ctrl'
    };

  }



  function QuizModuleCtrl($scope, $timeout, codeService, QuizData) {
    var vm = this;
    
    this.quiz = {
      'questions': QuizData.data
    };
    vm.nextPermalinkName = $scope.nextPermalinkName;
    this.quiz.questions[this.quiz.questions.length - 1].last = true;
    this.quizStarted = true;
    this.showTabs = true;
    this.userData = {
      'quizAnswers': {}
    };
    this.activeQuestionIndex = 0;

    this.activateQuestion = function (questionNumber) {
      this.setActiveQuestionIndex(questionNumber);
    };

    this.setActiveQuestionIndex = function (questionNumber) {
      this.activeQuestionIndex = questionNumber;
      this.activeQuestion = this.quiz.questions[questionNumber];
    };

    this.isAnswerCorrect = function (question) {
      var answer = this.userData.quizAnswers[question.id];
      if (question.type === 'code' && answer) {
        return codeService.compareCode(question.correctAnswer, answer);
      }
      return null != answer && answer == question.correctAnswer;
    };

    this.isAnswerIncorrect = function (question) {
      var answer = this.userData.quizAnswers[question.id];
      if (question.type === 'code' && answer) {
        return !codeService.compareCode(question.correctAnswer, answer);
      }
      return null != answer && answer != question.correctAnswer;
    };

    this.correctAnswerTexts =
    ["Yes, that's right!",
      "Good work!",
      "That's correct!",
      "You've got it right!",
      "Awesome! That's correct!"];

    this.incorrectAnswerTexts =
    ["Hmm... no. That's not right.",
      "That isn't correct.",
      "Oops, that is not the correct answer!",
      "Sorry, that isn't the correct answer.",
      "Nope, that isn't the right answer."];

    var index = Math.floor(Math.random() * 5);

    this.correctAnswerText = this.correctAnswerTexts[index];

    this.incorrectAnswerText = this.incorrectAnswerTexts[index];

    this.changed = function (val) {
      if (val) {
        this.userData.quizAnswers[this.activeQuestion.id] = val;
      }
    };

    this.submit = function () {
      vm.quizSubmitted = true;
      vm.totalCorrectAnswers =
        _.reduce(vm.quiz.questions, function (totalCorrectAnswers, question) {
          if (vm.isAnswerCorrect(question)) {
            totalCorrectAnswers++;
          }
          return totalCorrectAnswers;
        }, 0);
        
        
      if (Math.floor(vm.totalCorrectAnswers * 100/vm.quiz.questions.length) > 65) {
        // UserData.submitQuizData($scope.courseCode, QuizData.lessonName, this.userData);
    	console.log(this.userData);
        vm.quizComplete = true;  
      }
      else {
        vm.quizComplete = false;
      }
      
    };


  }

  function codeService() {

      var api = {};

      api.compareCode = function (answers, code) {
          if (!code) {
              return;
          }
          var regSpecialChars = /\s*([!@#$%^&*()_+\-=\[\]{};:\\|,.<>\/?])\s*/g;
          var regLineBreaks = /^\s+|\r+\n+|\n+|\r+|\s+$/g;
          var regSpaces = / +/g;
          // code = cs.strip(code);
          code = code.trim();
          code = code.replace(regLineBreaks, " ");
          code = code.replace(regSpaces, " ");
          code = code.replace(regSpecialChars, "$1");
              
          if( typeof answers === 'string' ) {
              answers = [ answers ];
          }
          for (var i = 0; i < answers.length; i++) {
              // var cs = new CommentStripper();
              var answer = answers[i];
              


              // answer = cs.strip(answer);
              if (!answer) {
                  return;
              }
              answer = answer.trim();
              answer = answer.replace(regLineBreaks, " ");
              answer = answer.replace(regSpaces, " ");
              answer = answer.replace(regSpecialChars, "$1");

              if (code == answer) return true;    
          }
          return false
      };

      return api;

  };
  
  function TranslateDirective() {
      return {
          restrict: "A",
          link: function(scope, elem, attrs) {
              scope.$watch(attrs.translateX, function(newVal, oldVal) {
                  if (newVal && newVal !== oldVal) {
                    newVal = parseFloat(newVal) + "%";
                    elem.css("transform", "translateX(" + newVal + ")"); 
                  } 
              });
          }
      };
  }

  
})(); 