// CampusConnect — small progressive-enhancement touches.
// Nothing here is required for the app to function; JSPs/servlets do the real work.

document.addEventListener('DOMContentLoaded', function () {

  // Composer: disable the post button until there's real content
  var composerText = document.querySelector('.composer textarea');
  var composerBtn = document.querySelector('.composer-submit');
  if (composerText && composerBtn) {
    var toggle = function () {
      composerBtn.disabled = composerText.value.trim().length === 0;
    };
    composerText.addEventListener('input', toggle);
    toggle();
  }

  // Post-type quick chips inside the composer just set a hidden field
  var typeChips = document.querySelectorAll('.post-type-select [data-post-type]');
  var hiddenType = document.querySelector('input[name="postType"]');
  typeChips.forEach(function (chip) {
    chip.addEventListener('click', function () {
      typeChips.forEach(function (c) { c.classList.remove('pill-active'); });
      chip.classList.add('pill-active');
      if (hiddenType) hiddenType.value = chip.getAttribute('data-post-type');
    });
  });

  // Onboarding: keep a live count of how many interests are selected
  var interestBoxes = document.querySelectorAll('.interest-chips input[type="checkbox"]');
  var counter = document.getElementById('interest-count');
  if (interestBoxes.length && counter) {
    var updateCount = function () {
      var n = document.querySelectorAll('.interest-chips input[type="checkbox"]:checked').length;
      counter.textContent = n + (n === 1 ? ' interest selected' : ' interests selected');
    };
    interestBoxes.forEach(function (box) { box.addEventListener('change', updateCount); });
    updateCount();
  }
});
